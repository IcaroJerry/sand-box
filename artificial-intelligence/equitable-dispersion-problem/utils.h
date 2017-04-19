float get_distancia(float **matriz, int e1, int e2);

void print_matriz(float **matriz, int tam);
void print_elementos_distancia_positiva(float **matriz, int tam);

float **init_matrix_element(float **matrix, char *filename, int num_elements);

float **create_dinamic_matrix(int i, int j);

float **release_matrix (int i, int j, float **matrix);

int get_num_elements(char *filename);

void init_rand();

int random_l_h(int min, int max);

float rand_01();

int flip();

void gera_sol_inicial(int *sol, int v);
void escreve_sol(int *sol, int v);
int get_num_elements_sol(int *sol, int v);

int *remove_negative(float **mat, int *sol, int n_elements);
