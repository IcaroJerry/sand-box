#ifndef INFO_PROBLEM_H
#define INFO_PROBLEM_H

#define MAX_OBJ 1000		// Numero maximo de objectos

// EStrutura para armazenar parametros
struct info
{
    // Tamanho da população
    int     popsize;
    // Probabilidade de mutação
    float   pm;
    // Probabilidade de recombinação
    float   pr;
    // Tamanho do torneio para seleção do pai da próxima geração
	int     tsize;
	// Constante para avaliação com penalização
	float   ro;
	// Número de elementos
    int     numElements;
	// Número de gerações
    int     numGenerations;
    
    int     mutacao_troca;
};

// Individuo (solução)
typedef struct individual chrom, *pchrom;

struct individual
{
    // Solução
    int   *p;
    // Valor da qualidade da solução
	float   fitness;
    
    float time_exec;
};

pchrom attr_pop(struct info d, int * previous_pop, int index);

pchrom init_pop(struct info d);

void print_pop(pchrom pop, struct info d);

chrom get_best(pchrom pop, struct info d, chrom best);

void write_best(chrom x, struct info d);

#endif
