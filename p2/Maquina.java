import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.lang.reflect.*;

class  Maquina {
   Filtradora poste;
   Tabla tabla;
   Stack pila;
   Vector prog;

   static int pc=0;
   int progbase=0;
   boolean returning=false;

   Method metodo;
   Method metodos[];
   Class c;
   Graphics g;
   double angulo;
   int x=0, y=0;
   Class parames[];
   Maquina(){ poste= new Filtradora( ); }
   public void setTabla(Tabla t){ tabla = t; }
   public void setGraphics(Graphics g){ this.g=g; }
   Maquina(Graphics g){ this.g=g; }
   public Vector getProg(){ return prog; }
   void initcode(){
      pila=new Stack();
      prog=new Vector();
   }
   Object pop(){ return pila.pop(); }

   int code(Object f){
      System.out.println("Gen ("+f+") size="+prog.size());
      prog.addElement(f);
      return prog.size()-1;
   }

   void execute(int p){
      String inst;
      System.out.println("progsize="+prog.size());
      for(pc=0;pc < prog.size(); pc=pc+1){
         System.out.println("pc="+pc+" inst "+prog.elementAt(pc));
      }
      for(pc=p; !(inst=(String)prog.elementAt(pc)).equals("STOP") && !returning;){
	 //for(pc=p;pc < prog.size();){
         try {
	    //System.out.println("111 pc= "+pc);
	    inst=(String)prog.elementAt(pc);
	    pc=pc+1;
	    System.out.println("222 pc= "+pc+" instr "+inst);
            c=this.getClass();
	    //System.out.println("clase "+c.getName());
            metodo=c.getDeclaredMethod(inst, null);
	    metodo.invoke(this, null);
	 } catch(NoSuchMethodException e){
				System.out.println("No metodo "+e);
         } catch(InvocationTargetException e){
				System.out.println(e);
         } catch(IllegalAccessException e){
				System.out.println(e);
         }
      }
   }
   void constpush(){
      Simbolo s;
      Double d;
      s=(Simbolo)prog.elementAt(pc);
      pc=pc+1;
      pila.push(new Double(s.val));
   }
   void varpush(){
      Simbolo s;
      double d;
      //System.out.println("varpush ");
      s=(Simbolo)prog.elementAt(pc);
      pc=pc+1;
      pila.push(s);
   }
   void color(){
      Color colors[]={Color.red,Color.green,Color.blue};
      double d1;
      d1=((Double)pila.pop()).doubleValue();
      if(g!=null){
         g.setColor(colors[(int)d1]);
      }
   }
   void line(){
	double corx, cory, corx2, cory2;
      double d1;
      d1=((Double)pila.pop()).doubleValue();
      cory=((Double)pila.pop()).doubleValue();
      corx2=((Double)pila.pop()).doubleValue();
      cory2=((Double)pila.pop()).doubleValue();
      System.out.println("La corx="+d1+" cory="+cory+"corx2="+corx2+"cory2"+cory2);
      System.out.println("El valor de x es: "+((int)(x+d1*Math.cos(angulo))+150));
      System.out.println("El valor de y es: "+((int)(x+d1*Math.cos(angulo))+150));
      if(g!=null){
         (new Linea((int)corx2,(int)cory2,(int)d1, (int)cory)).dibuja(g);
      }
      x=(int)(x+d1*Math.cos(angulo));
      y=(int)(y+d1*Math.sin(angulo));
      System.out.println("x="+x+" y="+y+" d1="+d1);
   }
   void circulo(){
      double corx, cory;
      double d1;
      d1=((Double)pila.pop()).doubleValue();
      corx=((Double)pila.pop()).doubleValue();
      cory=((Double)pila.pop()).doubleValue();

      System.out.println("El valor de d1="+d1+" corx="+corx+" cory="+cory);

      if(g!=null){
         (new Circulo((int)corx, (int)cory, (int)d1)).dibuja(g);
      }
   }
   void rectangulo(){
	double cor1, cor2, ancho, alto;
	ancho=((Double)pila.pop()).doubleValue();
   alto=((Double)pila.pop()).doubleValue();
   cor1=((Double)pila.pop()).doubleValue();
   cor2=((Double)pila.pop()).doubleValue();
   System.out.println("Las coordenadas son: x="+cor1+" y="+cor2+" ancho="+ancho+" alto="+alto);
      if(g!=null){
         (new Rectangulo( (int)cor1, (int)cor2, (int)ancho, (int)alto )).dibuja(g);
      }
   }
   void draw(){      
      Simbolo s;
      Dibujable dibu;       
      s=(Simbolo)pila.pop();
      System.out.println("draw ( "+s.nombre+" )");
      dibu=s.obtenDibu();
      if(g!=null && dibu != null){
         System.out.println("draw DIBUJAR");
         dibu.dibuja(g);
         pila.push(dibu);
      }
   }
   void filtro(){
      Simbolo s;
      Dibujable dibu;
      Imagen ima;
      System.err.println("FILTRO filtro");         
      s=(Simbolo)pila.pop();
      System.out.println("draw ( "+s.nombre+" )");
      dibu=s.obtenDibu(); 
      if(dibu != null){    
         System.out.println("if filtro");
         ima=(Imagen)dibu;
         //poste.set_Escala_de_Grises((BufferedImage)ima.ima);
         float[] kernel = new float[9];
         for (int i = 0; i < 9; i++) {
            kernel[i] = (float)(((Double)pila.pop()).doubleValue());
         }
         System.out.println("desp for filtro");
         poste.ponFiltro(new Kernel(3, 3, kernel));
         poste.procesa("User", (BufferedImage)ima.ima);
         new Imagen(poste.getFoto()).dibuja(g) ;
      }
      /*poste.set_Escala_de_Grises(leeImagen("Imagen.jpg"));
      System.err.println("antes dibujar ");
      new Imagen(poste.getFoto(), 100, 100, con).dibujar(g);*/        
   }
   void print(){
      Double d;
      d=(Double)pila.pop();
      System.out.println(""+d.doubleValue());
   }
   void prexpr(){
      Double d;
      d=(Double)pila.pop();
      System.out.print("["+d.doubleValue()+"]");
   }
}
