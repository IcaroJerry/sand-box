#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "parameters.h"

void parm_init_void(char *parm_name, Parm *parm) {

    sprintf(parm->name, "%s", parm_name);
    sprintf(parm->source, "%s", "[MANUAL]");
    
    parm->size = 0;
    parm->capacity = 1;
    parm->data = malloc(sizeof(float) * parm->capacity);
}
void parm_init(char *parm_name, char *filename, Parm *parm) {

    FILE    *f;
    int     i;
    float data_tmp;

    f = fopen(filename, "rt");
    if (!f) {
        printf("File not found!\n");
        exit(1);
    }

    sprintf(parm->name, "%s", parm_name);
    sprintf(parm->source, "%s", filename);
    
    parm->size = 0;
    parm->capacity = 1;
    parm->data = malloc(sizeof(float) * parm->capacity);

    while(!feof(f)) {
        fscanf(f, "%f", &data_tmp);
        parm_append(parm, data_tmp);
    }
}

void parm_append(Parm *parm, float value) {
  int i;
    for(i = 0; i < parm->size; ++i)
        if(parm_get(parm, i) == value)
            return;
    
    if(parm_is_full(parm) == 1)
        parm_increase_capacity(parm);

  parm->data[parm->size++] = value;
}

float parm_get(Parm *parm, int index) {
  if (index >= parm->size || index < 0) {
    printf("Index %d out of bounds for parm of size %d\n", index, parm->size);
    exit(1);
  }
  return parm->data[index];
}

void parm_set(Parm *parm, int index, float value) {

  while (index >= parm->size) {
    parm_append(parm, 0);
  }

  parm->data[index] = value;
}

void parm_increase_capacity(Parm *parm) {
    float *data_tmp;
    
    parm->capacity++;

    data_tmp = realloc(parm->data, sizeof(int) * parm->capacity);
    if(data_tmp == NULL) {
        printf("Error realloc parm!");
        exit(1);
    }

    parm->data = data_tmp;
}

void parm_free(Parm *parm) {
  free(parm->data);
} 

int parm_is_full(Parm *parm) {
  if (parm->size >= parm->capacity) {
    return 1;
  }
  return 0;
}

void parm_to_string(Parm *parm) {
    printf("[");
    int i;
    for(i = 0; i < parm->size; ++i) {
        float f_parm = parm_get(parm, i);
        int i_parm = (int) f_parm;
          if((double)f_parm == (double) i_parm)
            printf("%d", i_parm);
          else {
            if(((int)(f_parm * 10000)) <= 9900 && ((int)(f_parm * 10000)) >= 1000)
                printf("%.2f", f_parm);   
            else if (((int)(f_parm * 10000)) <= 990 && ((int)(f_parm * 10000)) >= 100)
                printf("%.3f", f_parm);
            else if (((int)(f_parm * 10000)) <= 90 && ((int)(f_parm * 10000)) >= 10)
                printf("%.4f", f_parm);
            else
                printf("%.5f", f_parm);
              
        }

        if(i <  parm->size -1)
            printf("; ");
    }
    printf("]");
}

void parm_print_parms(Parm parms[], int length) {
    int i;
    printf("Configuracoes dos parametros:\n");
    for(i = 0; i < length; ++i) {
        printf("\tParametro: %s\t", parms[i].name);
        printf("\tValor");
        if(parms[i].size > 1)
            printf("es");
        printf(": ");
        parm_to_string(&parms[i]);
        printf("\n");
    } 
}
