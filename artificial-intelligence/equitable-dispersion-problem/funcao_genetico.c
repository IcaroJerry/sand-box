#ifndef FUNCAO_GENETICO_C
#define FUNCAO_GENETICO_C
#define _CRT_SECURE_NO_WARNINGS 1
#include <stdio.h>
#include <stdlib.h>
#include "algoritmo_genetico.h"
#include "funcao_genetico.h"
#include "utils.h"


float eval_individual(int sol[], struct info d, float **mat)
{
    int     i, j;
    int     count_elem_G1 = 0;
    float   sum_distance = 0;
    float   avg_distance;
    // Percorre todos os objectos
    for (i = 0; i < d.numElements; i++) {
        // Verifica se o objecto i esta no sub-grupo G1
        if (sol[i] == 0)
        {
            ++count_elem_G1;
            for(j = i; j < d.numElements; ++j) {
                if (sol[j] == 0) {
                    sum_distance += get_distancia(mat, i+1, j+1);// Actualiza o somatório de
                }
            }
        }
    }
    avg_distance = sum_distance / count_elem_G1;
    
    return avg_distance;
}

void evaluate_sol(pchrom pop, struct info d, float **mat) {
    int i;

    for (i=0; i<d.popsize; i++)
        pop[i].fitness = eval_individual(pop[i].p, d, mat);
}

#endif
