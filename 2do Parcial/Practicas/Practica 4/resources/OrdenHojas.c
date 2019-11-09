#include <stdio.h>
#include <stdlib.h>

typedef struct NodoA
{
	int raiz;
	struct NodoA* izq;
	struct NodoA* der;
}*Arbin;

Arbin vacioA() {return NULL;}
Arbin consA(int r, Arbin i, Arbin d)
{
	Arbin t = (Arbin)malloc(sizeof(struct NodoA));
	t -> raiz = r;
	t -> izq = i;
	t -> der = d;
	return t;
}

int esvacioA(Arbin a) {return a == NULL;}
int raiz(Arbin a) {return a -> raiz;}
Arbin izquierdo(Arbin a) {return a -> izq;}
Arbin derecho(Arbin a) {return a -> der;}

void ImpPreOrd (Arbin a)
{
	if (!esvacioA(a))
	{
		printf("%d ", raiz(a));
		ImpPreOrd(izquierdo(a));
		ImpPreOrd(derecho(a));
	}
}

typedef Arbin DicBin;

DicBin InsOrd(int e, DicBin a)
{
	if(esvacioA(a))
		return consA(e, vacioA(), vacioA());
	if (e < raiz(a))
		return consA(raiz(a), InsOrd(e, izquierdo(a)), derecho(a));
	else
		return consA(raiz(a), izquierdo(a), InsOrd(e, derecho(a)));
}

int main(){
	DicBin arbol = vacioA();
	int N;
	scanf("%d", &N);
	int hojas[N];

	for(int i = 0; i < N; i++)
		scanf("%d", &hojas[i]);

	for(int i = N - 1; i > -1; i--)
		arbol = InsOrd(hojas[i], arbol);

	ImpPreOrd(arbol);
	return 0;
}