#define _CRT_SECURE_NO_WARNINGS 1
#include "algoritmo.h"
#include "funcao.h"
#include "utils.h"
#define GENERATIONS_TC 100
#define PROBGERAVIZ 1.0

// Calcula a qualidade de uma solução
// Parâmetros de entrada: solução (sol), capacidade da mochila (d), matriz com dados do problema (mat) e numero de objectos (v)
// Parâmetros de saída: qualidade da solução (se a capacidade for excedida devolve 0)
float eval_individual(int sol[], struct info d, int mat[][2], int *v)
{
	int     i;
	float   sum_weight, sum_profit;

	sum_weight = sum_profit = 0;
	// Percorre todos os objectos
	for (i=0; i < d.numGenes; i++)
	{
        // Verifica se o objecto i esta na mochila
		if (sol[i] == 1)
		{
            // Actualiza o peso total
			sum_weight += mat[i][0];
            // Actualiza o lucro total
			sum_profit += mat[i][1];
		}
	}
	if (sum_weight > d.capacity)
	{
        // Solucao inválida
		*v = 0;
		return 0;
	}
	else
	{
        // Solucao válida
		*v = 1;
		return sum_profit;
	}
}

//quando uma solução é inválida, retira-se aleatóriamente algum objeto da solução e torna a verificar se é válido. Isso se repete até ter uma solução válida
float eval_individual_reparo(int sol[], struct info d, int mat[][2], int *v)
{
	int     i;
	float   sum_weight, sum_profit;

	sum_weight = sum_profit = 0;
	// Percorre todos os objectos
	for (i=0; i < d.numGenes; i++)
	{
        // Verifica se o objecto i esta na mochila
		if (sol[i] == 1)
		{
            // Actualiza o peso total
			sum_weight += mat[i][0];
            // Actualiza o lucro total
			sum_profit += mat[i][1];
		}
	}
	if (sum_weight > d.capacity)
	{
        do
        {
            //gero uma posição randomica do vetor
            i = random_l_h(0, d.numGenes - 1);

            // Verifica se o objecto i esta na mochila
            if (sol[i] == 1)
            {
                sol[i] = 0;
                // Actualiza o peso total
                sum_weight -= mat[i][0];
                // Actualiza o lucro total
                sum_profit -= mat[i][1];
            }
        } while(sum_weight > d.capacity);
    }

    // Solucao válida
    *v = 1;
    return sum_profit;
}

float eval_individual_reparo_consciente(int sol[], struct info d, int mat[][2], int *v, int maximizar)
{
	int     i;
	float   sum_weight, sum_profit;

	sum_weight = sum_profit = 0;
	// Percorre todos os objectos
	for (i=0; i < d.numGenes; i++)
	{
        // Verifica se o objecto i esta na mochila
		if (sol[i] == 1)
		{
            // Actualiza o peso total
			sum_weight += mat[i][0];
            // Actualiza o lucro total
			sum_profit += mat[i][1];
		}
	}
	if (sum_weight > d.capacity)
	{
        int pos = -1;
        float mv;
        do
        {
            for (i=0; i < d.numGenes; i++)
            {
                // Verifica se o objecto i esta na mochila
                if (sol[i] == 1)
                {
                    //Se o problema for de maximização
                    if(maximizar == 1) //Se true
                        if(pos == -1 || mv > mat[i][1])
                        {
                            mv = mat[i][1];
                            pos = i;
                        }
                    else
                        if(pos == -1 || mv < mat[i][1])
                        {
                            mv = mat[i][1];
                            pos = i;
                        }
                }
            }

            sol[pos] = 0;
            // Actualiza o peso total
            sum_weight -= mat[pos][0];
            // Actualiza o lucro total
            sum_profit -= mat[pos][1];
        } while(sum_weight > d.capacity);
    }

    // Solucao válida
    *v = 1;
    return sum_profit;
}

//com penalização
float eval_individual_penalizado(int sol[], struct info d, int mat[][2], int *v)
{
	int     i;
	float   sum_weight, sum_profit;

	sum_weight = sum_profit = 0;
	// Percorre todos os objectos
	for (i=0; i < d.numGenes; i++)
	{
        // Verifica se o objecto i esta na mochila
		if (sol[i] == 1)
		{
            // Actualiza o peso total
			sum_weight += mat[i][0];
            // Actualiza o lucro total
			sum_profit += mat[i][1];

            if(d.ro < (float) mat[i][1]/ mat[i][0])
                d.ro = (float) mat[i][1]/ mat[i][0];
		}
	}
	if (sum_weight > d.capacity)
	{
        // Solucao inválida
		*v = 0;
		//return 0;
        //return calculate_penalty();
        int penalty = (sum_weight - d.capacity) * d.ro;
        return sum_profit - penalty; //retorna
	}
	else
	{
        // Solucao válida
		*v = 1;
		return sum_profit;
	}
}

// Avaliacao da população
// Parâmetros de entrada: populacao (pop), estrutura com parametros (d) e matriz com dados do problema (mat)
// Parâmetros de saída: Preenche pop com os valores de fitness e de validade para cada solução
void evaluate(pchrom pop, struct info d, int mat[][2])
{
	int i;

	for (i=0; i<d.popsize; i++)
		//pop[i].fitness = eval_individual(pop[i].p, d, mat, &pop[i].valido);
        //pop[i].fitness = eval_individual_penalizado(pop[i].p, d, mat, &pop[i].valido);
        //pop[i].fitness = eval_individual_reparo(pop[i].p, d, mat, &pop[i].valido);
        pop[i].fitness = eval_individual_reparo_consciente(pop[i].p, d, mat, &pop[i].valido, 1); //maximizar
        
}

//trepa_colinas basico
void trepa_colinas(pchrom pop, struct info d, int mat [][2]) {
    int i, j;
    chrom vizinho;
    
    for (i = 0; i < d.popsize; ++i) {
        for (j = 0; j < GENERATIONS_TC; ++j) {
            gera_vizinho(pop[i].p, vizinho.p, mat, d.numGenes);
            vizinho.fitness = eval_individual(vizinho.p, d, mat, &vizinho.valido);
            if (vizinho.fitness >= pop[i].fitness)
                pop[i] = vizinho;
            //pode-se adicionar recrestalização cristalizada, por exemplo...
        }
    }
}

void gera_vizinho (int sol [], int solViz, int mat[][2], int nGenes) {
    int i, menorCustoIn, maiorCustoOut, p1,  p2;
    
    //Copia a Solucao para a Solucao vizinha
    for (i = 0; i < nGenes; ++i)
        solViz[i] = sol [i];
    if (rand_01() < PROBGERAVIZ) {
        //escolhe um objecto aleatóriamente
        i = random_l_h(0, nGenes - 1);
        solViz[i] = !solViz[i]; 
    }
    else {
        menorCustoIn =  MAX_OBJ;
        maiorCustoOut = 0;
        
        for (i = 0; i < nGenes; ++i) {
            if (sol[i] == 1 && menorCustoIn > mat[i][1]) {
                menorCustoIn = mat[i][1];
                p1 = i;
            }
            if (sol[i] == 0 && maiorCustoOut < mat[i][1]) {
                maiorCustoOut = mat [i] [1];
                p2 = i;
            }
        }
        solViz[p1] = 0;
        solViz[p2] = 1;
    }
        
}
