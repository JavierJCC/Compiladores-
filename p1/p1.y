/*ENTRADAAS
V^V
V^F
F^V
F^F
VvV
VvF
FvV
FvF
-F
-V
(V^V)
(V^F)
(F^V)
(F^F)
(VvV)
(VvF)
(FvV)
(FvF)
(-F)
(-V)
*/

/*Declaraciones*/
%{
  #include <stdio.h>
  #include <ctype.h>
  #define YYSTYPE int /*Pila de YACC*/
  char *progname;
  int lineno = 1;
  void warning(char *s,char *y);
  void yyerror(char *s);
  int yylex();  
%}

/*Componente lexico o Terminal*/
%token RES
/*Precedencia de operadores*/
%left 'v'
%left '^'
%left '-'

/*Reglas*/
%%  
  
  list : /*NADA*/
     | list '\n'
     | list expr '\n'     
     ;

  expr : RES         {$$ = $1;}     
     | expr 'v' expr { if(($1 || $3) == 0 ) printf("F\n"); else printf("T\n");}
     | expr '^' expr { if(($1 && $3) == 0 ) printf("F\n"); else printf("T\n");}
     | '-' expr      { if($2 == 0 ) printf("T\n"); else printf("F\n"); }  
     | '(' expr ')'  {$$ = $2;} 
     ;
/*Codigo*/
%%
  /*Funcion principal*/
  void main (int argc, char *argv[]){
    progname= argv[0];
      yyparse ();
  }
  /*Funcion que hace el analisis lexico*/
  int yylex (){
      int c;
      while ((c = getchar ()) == ' ' || c == '\t' )  
        ;
    if (c == EOF)  return 0;
    
    if (isupper(c)){            
          if(c == 'F'){
            ungetc(c-22,stdin);                       
            }else if(c == 'V'){
            ungetc(c-37,stdin);   /*Obtenemos uno o cero*/                    
          }
            scanf ("%d", &yylval);
            return RES;             
      }
      if(c == '\n')
      lineno++;
    return c;                                
  }
  /* Llamada por yyparse ante un error */
  void yyerror (char *s){
    warning(s, (char *) 0);
  }

  void warning(char *s, char *t){
    fprintf (stderr, "%s: %s", progname, s);
    if(t) fprintf (stderr, " %s", t);
    fprintf (stderr, "cerca de la linea %d\n", lineno);
  }
  

