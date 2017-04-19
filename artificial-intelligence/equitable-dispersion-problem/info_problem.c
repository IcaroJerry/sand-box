#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "info_problem.h"
#include "utils.h"


void write_best(chrom x, struct info d) {
    int i;
    int count_elem = 0;
    
    printf("%.2f", x.fitness);
    printf("\n> Conjunto M: ");
    for (i = 0; i < d.numElements; i++) {
        if(x.p[i]==0) {
            ++count_elem;
            printf("%2d  ", i+1);
        }
    }
    if(d.mutacao_troca == 0)
        printf("\n> Tipo de Algoritmo Utilizado: Padrao");
    else
        printf("\n> Tipo de Algoritmo Utilizado: Com Mutacao Por Troca");
    printf("\n> Numero Elementos Seleccionados: %d", count_elem);
    printf("\n> Parametros utilizados:");
    printf("\n>> POPSIZE: %d", d.popsize);
    printf("\n>> T_SIZE: %d", d.tsize);
    printf("\n>> NUM_GENERATIONS: %d", d.numGenerations);
    printf("\n>> PM: %.2f", d.pm);
    printf("\n>> PR: %.2f", d.pr);
    putchar('\n');
}

chrom get_best(pchrom pop, struct info d, chrom best)
{
    int i;
    
    for (i = 0; i < d.popsize; i++)
    {
        if (best.fitness < pop[i].fitness)
            best = pop[i];
    }
    return best;
}


pchrom init_pop(struct info d)
{
    int     i, j;
    pchrom  indiv;
    
    indiv = malloc (sizeof(chrom) * d.popsize);
    
    for(i = 0; i < d.popsize; ++i)
        indiv[i].p = malloc(sizeof(int) * d.numElements);
    
    if (indiv == NULL) {
        printf("Erro na alocacao de memoria\n");
        exit(1);
    }
    
    for (i = 0; i < d.popsize; i++) {
        for (j = 0; j < d.numElements; j++)
            indiv[i].p[j] = flip();
    }
    
    return indiv;
}

pchrom attr_pop(struct info d, int *previous_pop, int index)
{
    int     i, j;
    pchrom  indiv;
    
    indiv = malloc (sizeof(chrom) * d.popsize);
    
    for(i = 0; i < d.popsize; ++i)
        indiv[i].p = malloc(sizeof(int) * d.numElements);
    
    if (indiv == NULL) {
        printf("Erro na alocacao de memoria\n");
        exit(1);
    }
    
    for (i = 0; i < d.popsize; i++) {
        for (j = 0; j < d.numElements; j++)
            indiv[i].p[j] = flip();
    }
    
    indiv[index].p = previous_pop;
    
    return indiv;
}

