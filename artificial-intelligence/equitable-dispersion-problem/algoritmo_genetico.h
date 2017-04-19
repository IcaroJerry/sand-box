#include "info_problem.h"

void tournament(pchrom pop, struct info d, pchrom parents);

void genetic_operators(pchrom parents, struct info d, pchrom offspring);

void crossover(pchrom parents, struct info d, pchrom offspring);

void mutation(pchrom offspring,struct info d);

void mutation_por_troca(pchrom offspring,struct info d);
