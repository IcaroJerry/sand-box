#define PM_FILE "config_params/ag-genetico/pm"
#define POP_FILE "config_params/ag-genetico/pop"
#define PR_FILE "config_params/ag-genetico/pr"
#define T_SIZE_FILE "config_params/ag-genetico/t_size"
#define MAX_GEN_FILE "config_params/ag-genetico/max_gen"
#define MUTACAO_TROCA_FILE "config_params/ag-genetico/mutacao_troca"
#define IT_FILE "config_params/ag-local/it"
#define PROB_FILE "config_params/ag-local/prob"
#define T_MAX_FILE "config_params/ag-local/t_max"
#define T_MIN_FILE "config_params/ag-local/t_min"
#define RECRIST_FILE "config_params/ag-local/recristalizacao"
#define HIB_ADV_MODE_FILE "config_params/ag-hib/adv_mode"
#define RUNS_FILE "config_params/runs"


typedef struct {
  char source[100];
  char name[100];
  int size;      
  int capacity;  
  float *data; 
} Parm;

void parm_init_void(char *parm_name, Parm *parm);

void parm_init(char *parm_name, char *filename, Parm *parm);

void parm_append(Parm *parm, float value);

float parm_get(Parm *parm, int index);

void parm_set(Parm *parm, int index, float value);

void parm_increase_capacity(Parm *parm);

void parm_free(Parm *parm);

int parm_is_full(Parm *parm);

void parm_to_string(Parm *parm);

void parm_print_parms(Parm parms[], int length);
