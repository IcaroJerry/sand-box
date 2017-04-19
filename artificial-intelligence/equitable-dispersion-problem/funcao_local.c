#include "funcao_local.h"
#include "utils.h"

float calcula_fit(int a[], float **mat, int n_elements)
{
    int i, j;
    float dist_total = 0, avg_distance;
    int count_elem_G1 = 0;

    for(i=0; i < n_elements; i++)
        if(a[i]==0) {
            ++count_elem_G1;
            for(j = i; j < n_elements;j++)
                if(a[j] == 0)
                    dist_total += get_distancia(mat, i+1, j+1);
        }
        
    avg_distance = dist_total / count_elem_G1;
    return avg_distance;
}
