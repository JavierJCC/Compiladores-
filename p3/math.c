#include <math.h>
#include <stdio.h>
#include <errno.h>
#include "hoc.h"

/*Funciones para evaluar*/
char And (char x, char y){
	//printf("Los valores son: %c Y %c \n",x, y);
	if(x == 'f' || y == 'f'){
			return 'f';
		}else{
			return 't';
		}
}

char Or (char x, char y){
	//printf("Los valores son: %c Y %c \n",x, y);
	if(x == y){
			return x;
		}else{
			return 't';
		}
}

char Not (char x){
	//printf("Los valor es: %c \n",x);
	if(x == 't'){
		return 'f';
	}else{
		return 't';
	}
}

