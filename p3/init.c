#include <math.h>
#include "hoc.h"
#include "y.tab.h"
#define NULL 0

extern char And(), Or(), Not();

static struct {
	char *name;
	char value;
} consts[] = {
	{"FALSE",    'f'},
	{"TRUE",    't'},
	{NULL,    0},
};

static struct {
	char *name;
	double (*func)();
} builtins[] = {
	{NULL,    NULL},
};

void init() {
	int i;
	Symbol *s;
	for(i = 0; consts[i].name; i++) {
		install(consts[i].name, VAR, consts[i].value);
	}
	for(i = 0; builtins[i].name; i++) {
		s = install(builtins[i].name, BLTIN, 't');
		s->u.ptr = builtins[i].func;
	}
}
