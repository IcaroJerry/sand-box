#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "algoritmo_local.h"
#include "funcao_local.h"
#include "utils.h"


void gera_vizinho(int a[], int b[], int n)
{
    int i, p1;
    
    for(i = 0; i < n; i++)
        b[i] = a[i];
    
    p1 = random_l_h(0, n-1);
    
    if (b[p1] == 1)
        b[p1] = 0;
    else
        b[p1] = 1;
}

float trepa_colinas_recristalizacao_simulada(int sol[], float **mat, int vert, int num_iter, float percent_prob, double temp_min, double temp_max)
{
    int *nova_sol, i;
    int *best_sol;
    float custo, custo_viz, best_custo;
    double t, delta;
    
    nova_sol = malloc(sizeof(int) * vert);
    best_sol = malloc(sizeof(int) * vert);
    if(nova_sol == NULL || best_sol == NULL)
    {
        printf("Erro na alocacao de memoria");
        exit(1);
    }
    // Avalia solucao inicial
    custo = calcula_fit(sol, mat, vert);
    best_custo = custo;
    substitui(best_sol, sol, vert);
    
    t = temp_max;
    delta = (temp_max - temp_min) / num_iter;
    for(i = 0; i < num_iter; i++)
    {
        // Gera vizinho
        gera_vizinho(sol, nova_sol, vert);
        // Avalia vizinho
        custo_viz = calcula_fit(nova_sol, mat, vert);
        // Aceita vizinho se o custo diminuir (problema de minimizacao)
        if(custo_viz > best_custo)
        {
            best_custo =  custo_viz;
            substitui(best_sol, nova_sol, vert);
        }
        if(custo_viz >= custo)
        {
            substitui(sol, nova_sol, vert);
            custo = custo_viz;
        }
        //código adicional para o trepa colinas probabilistico
        else
        {
            double var = (double)(custo_viz - custo);
            double var_result = exp(var);
            percent_prob = var_result / t;
            if(rand_01() < percent_prob)
            {
                substitui(sol, nova_sol, vert);
                custo = custo_viz;
            }
        }
        t = t - delta;
    }
    free(nova_sol);
    return custo;
}

float trepa_colinas(int sol[], float **mat, int vert, int num_iter)
{
    int *nova_sol, i;
    float custo, custo_viz;
    nova_sol = malloc(sizeof(int) * vert);
    if(nova_sol == NULL)
    {
        printf("Erro na alocacao de memoria");
        exit(1);
    }
    // Avalia solucao inicial
    custo = calcula_fit(sol, mat, vert);
    for(i=0; i < num_iter; i++)
    {
        // Gera vizinho
        gera_vizinho(sol, nova_sol, vert);
        // Avalia vizinho
        custo_viz = calcula_fit(nova_sol, mat, vert);
        if(custo_viz >= custo) {
            substitui(sol, nova_sol, vert);
            custo = custo_viz;
        }
    }
    free(nova_sol);
    return custo;
}

float trepa_colinas_probabilistico(int sol[], float **mat, int vert, int num_iter, float percent_prob)
{
    int *nova_sol, i;
    int *best_sol;
    float custo, custo_viz;
    float best_custo;

    nova_sol = malloc(sizeof(int)*vert);
    best_sol = malloc(sizeof(int)*vert);
    if(nova_sol == NULL || best_sol == NULL)
    {
        printf("Erro na alocacao de memoria");
        exit(1);
    }
    // Avalia solucao inicial
    custo = calcula_fit(sol, mat, vert);
    best_custo = custo;
    substitui(best_sol, sol, vert);
    for(i=0; i<num_iter; i++)
    {
        // Gera vizinho
        gera_vizinho(sol, nova_sol, vert);
        // Avalia vizinho
        custo_viz = calcula_fit(nova_sol, mat, vert);
        // Aceita vizinho se o custo diminuir (problema de minimizacao)
        if(custo_viz > best_custo)
        {
            best_custo =  custo_viz;
            substitui(best_sol, nova_sol, vert);
        }
        if(custo_viz >= custo)
        {
            substitui(sol, nova_sol, vert);
            custo = custo_viz;
        }
        //código adicional para o trepa colinas probabilistico
        else
        {
            if(rand_01() < percent_prob)
            {
                substitui(sol, nova_sol, vert);
                custo = custo_viz;
            }
        }
    }
    free(nova_sol);
    return custo;
}

void substitui(int a[], int b[], int n)
{
    int i;
    for(i = 0; i < n; i++)
        a[i] = b[i];
}
