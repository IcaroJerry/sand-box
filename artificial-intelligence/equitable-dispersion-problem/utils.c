#define _CRT_SECURE_NO_WARNINGS 1
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "utils.h"

// Inicialização do gerador de números aleatórios
void init_rand() {
    srand((unsigned)time(NULL));
}



int get_num_elements(char *filename) {
    FILE    *f;
    int     i;
    int     num_elements;
    int     first_element, element_A, element_B;
    float   data_element_AB;
    
    f = fopen(filename, "rt");
    
    if (!f) {
        printf("File not found\n");
        exit(1);
    }
    
    fscanf(f, "%d %d %f", &first_element, &element_B, &data_element_AB);
    
    num_elements = 1;
    
    while(!feof(f)) {
        fscanf(f, "%d %d %f", &element_A, &element_B, &data_element_AB);
        if (first_element != element_A) {
            break;
        }
        ++num_elements;
    }
    
    ++num_elements;
    
    fclose(f);
    
    return num_elements;
}

// Simula o lançamento de uma moeda, retornando o valor 0 ou 1
int flip() {
    if ((((float)rand()) / RAND_MAX) < 0.5)
        return 0;
    else
        return 1;
}



// Devolve um valor inteiro distribuido uniformemente entre min e max
int random_l_h(int min, int max)
{
    return min + rand() % (max-min+1);
}

// Devolve um valor real distribuido uniformemente entre 0 e 1
float rand_01()
{
    return ((float)rand())/RAND_MAX;
}



float **create_dinamic_matrix(int i, int j)
{
    float **matrix; /* ponteiro para a matriz */
    int   index;    /* variavel auxiliar      */
    
    if (i < 1 || j < 1) { /* verifica parametros recebidos */
        printf("Erro na alocacao de memoria: matriz dos elementos\n");
        exit(1);
    }
    
    matrix = (float **) calloc (i, sizeof(float *));
    
    if (matrix == NULL) {
        printf("Erro na alocacao de memoria: matriz dos elementos\n");
        exit(1);
    }
    
    //aloca as colunas da matriz
    for (index = 0; index < j; ++index) {
        matrix[index] = (float*) calloc (j, sizeof(float));
        
        if (matrix[index] == NULL) {
            printf("Erro na alocacao de memoria: matriz dos elementos\n");
            exit(1);
        }
    }
    
    return matrix;
}

float **release_matrix(int row, int col, float **matrix) {
    int  index;
    
    if (matrix == NULL)
        return NULL;
    
    if (row < 1 || col < 1) {  /* verifica parametros recebidos */
        printf("Erro ao liberar de memoria: matriz dos elementos\n");
        exit(1);
    }
    
    for (index = 0; index < row; ++index)
        free (matrix[index]);
    
    free (matrix);
    
    return NULL;
}


float **init_matrix_element(float **matrix, char *filename, int num_elements) {
    FILE    *f;
    int     i, j, e1, e2;
    float *pesos;
    int total_pesos;
    int aux;
    
    
    total_pesos = num_elements *  (num_elements - 1) / 2;
    pesos = malloc( sizeof(float) * total_pesos);
    
    f = fopen(filename, "rt");
    
    if (!f) {
        printf("File not found\n");
        exit(1);
    }
    
    i = 0;
    while(!feof(f)) {
        fscanf(f, "%d\t%d\t%f", &e1, &e2, &pesos[i]);
        ++i;
    }
    
    aux = 0;
    
    for(j = 0; j < num_elements; ++j) {
        for(i = 0; i < num_elements; ++i) {
            if(i > j) {
                matrix[j][i] = pesos[aux];
                matrix[i][j] = pesos[aux];
                ++aux;
            }
            else
                matrix[i][j] = 0;
            
        }
    }
    return matrix;
}

float get_distancia(float **matriz, int e1, int e2) {
    if( e1 == e2)
        return 0;
    else if( e1 < 1  || e2 < 1) {
        printf("Elemento invalido");
        exit(1);
    }
    else if(e1 > e2)
        return matriz[e1-1][e2-1];
    else
        return matriz[e2-1][e1-1];
    
}

void print_matriz(float **matriz, int tam) {
    int i, j;
    printf("\n\t");
    
    for(j = 0; j < tam; ++j) 
        printf("%d\t", j+1);
    printf("\n");
    
    for(i = 0; i < tam; ++i) {
        printf("%d|\t", i+1);
        for(j = 0; j < tam; ++j) {
            if(i > j || i == j)
                printf("%.2f\t", matriz[i][j]);
        }
        printf("\n");
    }
}

void print_elementos_distancia_positiva(float **matriz, int tam) {
    int i, j;
    
    printf("{");
    
    for(i = 0; i < tam; ++i) {
        for(j = 0; j < tam; ++j) {
            if(i > j || i == j){
                if(get_distancia(matriz, i+1, j+1) > 0)
                    printf("[%d %d]\t" , i + 1 ,j + 1);
            }
        }
    }
    printf("}\n");
}



void gera_sol_inicial(int *sol, int v)
{
    int i;
    
    for(i = 0; i < v; i++)
        sol[i] = flip();
}

int get_num_elements_sol(int *sol, int v) {
    int i;
    int count_elem = 0;
    
    for(i = 0; i < v; i++)
        if(sol[i] == 0)
            ++count_elem;
     
        return count_elem;
}

void escreve_sol(int *sol, int v) {
    int i;
    
    for(i = 0; i < v; i++)
        if(sol[i] == 0)
            printf("%2d  ", i + 1);
    printf("\n");
}


int *remove_negative(float **mat, int *sol, int n_elements) {
    int i, j;
    float distance;
    //varre o array de solucoes
    for(i = 0; i < n_elements; ++i)
        //procura apenas os elementos do grupo 1 (0)
        if(sol[i] == 0) { 
          for(j = 0; j < n_elements; ++j)
            if (sol[j] == 0) {
                distance = get_distancia(mat, i+1, j+1);
                if(distance < 0) {
                    sol[i] = 1; //retira elemento da solucao
                }
            }
        }
       return sol; 
}
