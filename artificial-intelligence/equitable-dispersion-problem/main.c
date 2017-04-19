#define _CRT_SECURE_NO_WARNINGS 1
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "parameters.h"
#include "info_problem.h"
#include "utils.h"
#include "algoritmo_local.h"
#include "algoritmo_genetico.h"
#include "funcao_genetico.h"




int main(int argc, char *argv[]) {
    
    clock_t c2, c1, c1_exec, c2_exec;
    float time, time_exec;
    c1 = clock();
    
    const char *fich_default[] = {"tipo1/MDPI1_20.txt", "tipo1/MDPI1_150.txt", "tipo1/MDPI1_500.txt", "tipo1/MDPI2_25.txt", "tipo1/MDPI2_30.txt", "tipo1/MDPI2_150.txt", "tipo2/MDPII1_20.txt", "tipo2/MDPII1_150.txt", "tipo2/MDPII2_25.txt","tipo2/MDPII2_30.txt", "tipo2/MDPII2_150.txt", "tipo2/MDPII2_500.txt"};
    char nome_fich_exec[100];
    char algoritmo_exec[10];
    char parm_mode_exec[10];
    
    int n_elements = 0;
    int index = 0;
    int n_fich_exec;
    struct  info problem_data;
    float **mat;
    int a, b, c, d, e, h,  v, k; //indices auxiliares para os loops dos parametros
    
    //parametros gerais
    Parm p_runs;
    int r, runs;
    int conb = 0;
    int exec_total = 0;
    int exec_gen = 0;
    int exec_loc = 0;
    int exec_hib = 0;
    int label_ag, label_ag_1;

    int index_best_a, index_best_b, index_best_c, index_best_d, index_best_e;
    float best_time_exec = 0;
    
    //parametros para algoritmo local
    Parm it, prob, t_max, t_min, recrist;
    int *best, *sol, *best_all_cbn, numitera;
    float custo, best_custo = 0, best_custo_all_conb = 0;
    float percent_prob;
    double temp_min, temp_max;
    float loc_mbf =0.0;
    
    //parametros para algoritmo genetico
    Parm pm, ppop, pr, t_size, max_gen, p_mutacao_troca;
    struct info EA_param, best_conb_parm;
    pchrom      pop = NULL, parents = NULL;
    chrom       gen_best_run, gen_best_ever, gen_best_ever_all_conb;
    int         gen_actual;
    float       gen_mbf = 0.0;
    int         mutacao_troca;
    
    //parametros para algoritmo hibrido
    Parm p_hib_adv_mod;
    
    //Tratamento de argumentos de Entrada
    if(argc == 1){
        strcpy(nome_fich_exec, "--all");
        strcpy(algoritmo_exec, "--all");
        strcpy(parm_mode_exec, "--auto");
        printf("Executando todos os ficheiros, todos os algoritmos e parametros com valores default...\n");
    }
    else if(argc == 2){
        if (strcmp (argv[1], "--help") == 0) {
            printf("Sintaxe: main [nome_fic] [options] [parm_mode]\n");
            printf("Opcoes que podem ser utilizadas:\n");
            printf("--all = todas\n");
            printf("- Opcao para execucao de algoritmo:\n");
            printf("--gen = genetico\n");
            printf("--loc = local\n");
            printf("--hib = hibrido\n");
            printf("- Modo de preenchimento dos parameteros:\n");
            printf("--auto = automatic\n");
            printf("--manual = manual\n");
            printf("\n");
            exit(1);
        }
        else if (strcmp (argv[1], "--all") == 0 || strcmp (argv[1], "--gen") == 0 ||
            strcmp (argv[1], "--loc") == 0 || strcmp (argv[1], "--hib") == 0) {
            strcpy(algoritmo_exec, argv[1]);
        strcpy(nome_fich_exec, "--all");
        strcpy(parm_mode_exec, "--auto");
            }
            else if (strcmp (argv[1], "--auto") == 0 || strcmp (argv[1], "--manual") == 0) {
                strcpy(algoritmo_exec, "--all");
                strcpy(nome_fich_exec, "--all");
                strcpy(parm_mode_exec, argv[1]);
            }
            else{
                strcpy(nome_fich_exec, argv[1]);
                strcpy(algoritmo_exec, "--all");
                strcpy(parm_mode_exec, "--auto");
            }
    }
    else if(argc == 3) {
        strcpy(nome_fich_exec, argv[1]);
        if (strcmp (argv[2], "--all") == 0 || strcmp (argv[2], "--gen") == 0 ||
            strcmp (argv[2], "--loc") == 0 || strcmp (argv[2], "--hib") == 0) {
            strcpy(algoritmo_exec, argv[2]);
        strcpy(parm_mode_exec, "--auto");
            }
            else if (strcmp (argv[2], "--help") == 0) {
                printf("Sintaxe: main [nome_fic] [options] [parm_mode]\n");
                printf("Opcoes que podem ser utilizadas:\n");
                printf("--all = todas\n");
                printf("- Opcao para execucao de algoritmo:\n");
                printf("--gen = genetico\n");
                printf("--loc = local\n");
                printf("--hib = hibrido\n");
                printf("- Modo de preenchimento dos parameteros:\n");
                printf("--auto = automatic\n");
                printf("--manual = manual\n");
                printf("\n");
                exit(1);
            }
            else if (strcmp (argv[2], "--auto") == 0 || strcmp (argv[2], "--manual") == 0) {
                strcpy(parm_mode_exec, argv[2]);
                strcpy(algoritmo_exec, "--all");
            }
            else {
                printf("Sintaxe: main [nome_fic] [options] [parm_mode]; o comando --help para mais informacoes.\n");
                exit(1);
            }
    }
    else if(argc == 4) {
        strcpy(nome_fich_exec, argv[1]);
        if(strcmp (argv[2], "--all") == 0 || strcmp (argv[2], "--gen") == 0 ||
            strcmp (argv[2], "--loc") == 0 || strcmp (argv[2], "--hib") == 0) {
            strcpy(algoritmo_exec, argv[2]);    
            }
            else {
                printf("Sintaxe: main [nome_fic] [options] [parm_mode]; o comando --help para mais informacoes.\n");
                exit(1);
            }
            
            if (strcmp (argv[3], "--auto") == 0 || strcmp (argv[3], "--manual") == 0) {
                strcpy(parm_mode_exec, argv[3]);
            }
            else {
                printf("Sintaxe: main [nome_fic] [options] [parm_mode]; o comando --help para mais informacoes.\n");
                exit(1);
            }
    }
    else { //argc > 5
        printf("Sintaxe: main [nome_fic] [options] [parm_mode]; o comando --help para mais informacoes.\n");
        exit(1);
    }
    
    //Inicializa a geração dos números aleatórios
    init_rand();
    
    parm_init("MUTACAO POR TROCA", MUTACAO_TROCA_FILE, &p_mutacao_troca); //TODO: fazer tratamento manual
    
    if(strcmp (parm_mode_exec, "--auto") == 0) {
        parm_init("RUN", RUNS_FILE, &p_runs); //
        runs = (int) parm_get(&p_runs, 0);
        //Parametros para algoritmo Local
        parm_init("IT", IT_FILE, &it); //
        parm_init("PROB", PROB_FILE, &prob); //
        parm_init("RECRISTALIZACAO", RECRIST_FILE, &recrist); //
        parm_init("T_MAX", T_MAX_FILE, &t_max); //
        parm_init("T_MIN", T_MIN_FILE, &t_min); //
        
        //Parametros para algoritmo Genético
        parm_init("PM", PM_FILE, &pm); //Probabilidade de mutação
        parm_init("POP", POP_FILE, &ppop); //População inicial
        parm_init("PR", PR_FILE, &pr); //Probabilidade de recombinação
        parm_init("T_SIZE", T_SIZE_FILE, &t_size); //Número de Torneios
        parm_init("MAX_GEN", MAX_GEN_FILE, &max_gen); //Número máximo de gerações
        
        //Parametros para algoritmo Hibrido
        parm_init("MODO ADV", HIB_ADV_MODE_FILE, &p_hib_adv_mod); //Probabilidade de mutação
    }
    else {
        float opt;
        
        printf("Atribua valores aos parametros abaixo ou digite -1 para valores default.\n");
        printf("Preencendo Parametros Gerais do Sistema:\n");
        printf("> Numero de Execucoes para cada combinacao (int): ");
        scanf("%f", &opt);
        
        if(opt < 0)
            parm_init("RUN", RUNS_FILE, &p_runs); //
            else {
                parm_init_void("RUN", &p_runs);
                parm_append(&p_runs, opt);
            }
            
            runs = (int) parm_get(&p_runs, 0);
        
        //Parametros para algoritmo Local ou Hibrido
        if(strcmp (algoritmo_exec, "--all") == 0 || strcmp (algoritmo_exec, "--loc") == 0 ||  strcmp (algoritmo_exec, "--hib") == 0) { 
            printf("Preencendo Parametros para Algoritmos Locias ou Hibridos:\n");
            printf("> Iteracoes (int): ");
            scanf("%f", &opt);
            if(opt < 0)
                parm_init("IT", IT_FILE, &it);
            else {
                parm_init_void("IT", &it);
                parm_append(&it,  opt);
            }
            
            printf("> Trepa Colina (0 = Padrao | 1 = Recristalizacao | 2 = Probabilitico| -1 = Default): ");
            scanf("%f", &opt);
            if(opt != 0) {
                if(opt == -1) {
                    parm_init("RECRISTALIZACAO", RECRIST_FILE, &recrist); //
                }
                else {
                    parm_init_void("RECRISTALIZACAO", &recrist);
                    parm_append(&recrist,  opt);
                }
                printf("> Probabilidade  (float): ");
                scanf("%f", &opt);
                if(opt < 0)
                    parm_init("PROB", PROB_FILE, &prob);
                else {
                    parm_init_void("PROB", &prob);
                    parm_append(&prob,  opt);
                }
                
                if((int)parm_get(&recrist, 0) == 1) {
                    printf("> Temperatura Minima (double): ");
                    scanf("%f", &opt);
                    if(opt < 0)
                        parm_init("T_MIN", T_MIN_FILE, &t_min);
                    else {
                        parm_init_void("T_MIN", &t_min);
                        parm_append(&t_min,  opt);
                    }
                    
                    printf("> Temperatura Maxima (double): ");
                    scanf("%f", &opt);
                    if(opt < 0)
                        parm_init("T_MAX", T_MAX_FILE, &t_max);
                    else {
                        parm_init_void("T_MAX", &t_max);
                        parm_append(&t_max,  opt);
                    }
                }
                else {
                    parm_init_void("T_MAX", &t_max); //
                    parm_append(&t_max,  0);
                    parm_init_void("T_MIN", &t_min); //
                    parm_append(&t_min,  0);
                }
            }
            else {
                parm_init_void("RECRISTALIZACAO", &recrist);
                parm_append(&recrist,  0);
                parm_init_void("PROB", &prob);
                parm_append(&prob,  0);
                parm_init_void("T_MAX", &t_max); //
                parm_append(&t_max,  0);
                parm_init_void("T_MIN", &t_min); //
                parm_append(&t_min,  0);
            }
            
            //Parametros para algoritmo Genético
            parm_init("PM", PM_FILE, &pm); //Probabilidade de mutação
            parm_init("POP", POP_FILE, &ppop); //População inicial
            parm_init("PR", PR_FILE, &pr); //Probabilidade de recombinação
            parm_init("T_SIZE", T_SIZE_FILE, &t_size); //Número de Torneios
            parm_init("MAX_GEN", MAX_GEN_FILE, &max_gen); //Número máximo de gerações
        }
        
        //Parametros para algoritmo Genetico ou Hibrido
        if(strcmp (algoritmo_exec, "--all") == 0 || strcmp (algoritmo_exec, "--gen") == 0 ||  strcmp (algoritmo_exec, "--hib") == 0) {
            printf("Preencendo Parametros para Algoritmos Geneticos ou Hibridos:\n");
            printf("> Probabilidade de mutação (float): ");
            scanf("%f", &opt);
            if(opt < 0)
                parm_init("PM", PM_FILE, &pm);
            else {
                parm_init_void("PM", &pm);
                parm_append(&pm,  opt);
            }
            
            printf("> Probabilidade de recombinacao (float): ");
            scanf("%f", &opt);
            if(opt < 0)
                parm_init("PR", PR_FILE, &pr);
            else {
                parm_init_void("PR", &pr);
                parm_append(&pr,  opt);
            }
            
            printf("> Populacao inicial (int): ");
            scanf("%f", &opt);
            if(opt < 0)
                parm_init("POP", POP_FILE, &ppop);
            else {
                parm_init_void("POP", &ppop);
                parm_append(&ppop,  opt);
            }
            
            printf("> Tamanho dos Torneios (int): ");
            scanf("%f", &opt);
            if(opt < 0)
                parm_init("T_SIZE", T_SIZE_FILE, &t_size);
            else {
                parm_init_void("T_SIZE", &t_size);
                parm_append(&t_size,  opt);
            }
            
            printf("> Número máximo de geracoes (int): ");
            scanf("%f", &opt);
            if(opt < 0)
                parm_init("MAX_GEN", MAX_GEN_FILE, &max_gen);
            else {
                parm_init_void("MAX_GEN", &max_gen);
                parm_append(&max_gen,  opt);
            }
            
            //Parametros para algoritmo Local
            parm_init("IT", IT_FILE, &it); //
            parm_init("PROB", PROB_FILE, &prob); //
            parm_init("RECRISTALIZACAO", RECRIST_FILE, &recrist); //
            parm_init("T_MAX", T_MAX_FILE, &t_max); //
            parm_init("T_MIN", T_MIN_FILE, &t_min); //
            
        }
        
        //Parametros para algoritmo Hibrido
        if(strcmp (algoritmo_exec, "--hib") == 0) { 
            printf("Preencendo Parametros Especificos para o Algoritmo Hibrido:\n");
            printf("> AG Hibrido com Execucao em 3 etapas (0 = Nao | 1 = Sim | -1 = Default): ");
            scanf("%f", &opt);
            if(opt == -1) {
                parm_init("AVD MODE", HIB_ADV_MODE_FILE, &p_hib_adv_mod); //
            }
            else {
                parm_init_void("AVD MODE", &p_hib_adv_mod);
                parm_append(&p_hib_adv_mod,  opt);            
            }
        }
    }
    
    if(strcmp (algoritmo_exec, "--all") == 0 || strcmp (algoritmo_exec, "--loc") == 0 ||  strcmp (algoritmo_exec, "--hib") == 0) {
        Parm p_loc[5] = {it,  recrist, prob, t_max, t_min};
        parm_print_parms(p_loc, 5);
    }
    if(strcmp (algoritmo_exec, "--all") == 0 || strcmp (algoritmo_exec, "--gen") == 0 ||  strcmp (algoritmo_exec, "--hib") == 0) {
        Parm p_ag[6] = {pm, ppop, pr, t_size, max_gen, p_mutacao_troca};
        parm_print_parms(p_ag, 6);
    }
    if(strcmp (algoritmo_exec, "--hib") == 0) {
        Parm p_hib[1] = {p_hib_adv_mod};
        parm_print_parms(p_hib, 1);
    }
    
    //Faz uma pausa para que possa ser verificado os parametros caso sejam preenchidos manualmente
    if(strcmp (parm_mode_exec, "--manual") == 0) {
        //         printf("#Os parametros selecionados geraram %d combinacoes possiveis:\n", (p_hib_adv_mod.size + 1) *(it.size + 1)* (prob.size + 1)* (t_max.size + 1),
        //                                                                                  (t_min.size + 1)* (recrist.size + 1)* (pm.size + 1)* (ppop.size + 1)* (pr.size + 1), (t_size.size + 1)* (max_gen.size + 1) *(runs + 1));
        printf("Pressione qualquer tecla para continuar:\n");
        //system("pause");
        getchar();
        getchar();
    }
    
    //Se o número de execuções do processo for menor ou igual a 0, termina o programa
    if (runs <= 0) {
        printf("0 execucoes foram feitas.\n");
        return 0;
    }
    
    n_fich_exec = strcmp (nome_fich_exec, "--all");
    do {
        
        //TODO: Variavel que armazena a melhor solução (POR FICHEIRO) de todos os algoritmos
        //       inclusive o nome do algoritmo utilizado e os parameteros de configurações.
        
        printf("\n\n#################### %do Ficheiro em execucao:", index + 1);
        if(n_fich_exec == 0)
            strcpy(nome_fich_exec, fich_default[index]);
        else
            index = 12;
        //busca o número de elementos do ficheiro que será executado
        n_elements = get_num_elements(nome_fich_exec);
        printf(" %s  - Num Elementos: %d  ####################\n\n", nome_fich_exec, n_elements);
        
        //cria uma matriz para cada ficheiro que será executado
        mat = create_dinamic_matrix(n_elements, n_elements);
        //inicializa a matriz com dado do ficheiro
        mat = init_matrix_element(mat, nome_fich_exec, n_elements);
        
        //executa os algoritmos geneticos
        if(strcmp(algoritmo_exec, "--all") == 0 || strcmp(algoritmo_exec, "--gen") == 0) {            
            label_ag = label_ag_1 = 0;
            conb = 0;
            //Loop de execucão para os parametros: pm, ppop, pr, t_size, max_gen;
            for(h = 0; h < p_mutacao_troca.size; ++h) //Algoritmo Genetico com Mutacao por troca ou nao
                for(a = 0; a < t_size.size; ++a) //t_size
                    for(b = 0; b < max_gen.size; ++b) //max_gen
                        for(c = 0; c < ppop.size; ++c)//pop
                            for(d = 0; d < pm.size; ++d)//pm
                                for(e = 0; e < pr.size; ++e) {//pr
                                    EA_param.tsize = (int) parm_get(&t_size, a);
                                    EA_param.popsize = (int) parm_get(&ppop, c);
                                    //EA_param.numGenerations = (int) parm_get(&max_gen, b);
                                    EA_param.numGenerations  = (int) (100 * parm_get(&max_gen, b) )/ EA_param.popsize;
                                    EA_param.pm = parm_get(&pm, d);
                                    EA_param.pr =  parm_get(&pr, e);
                                    EA_param.numElements = n_elements;
                                    EA_param.mutacao_troca =  (int) parm_get(&p_mutacao_troca, h);
                                    
                                    gen_mbf = 0; //zerando a media das escolhidas
                                    
                                    if(EA_param.mutacao_troca == 0) {
                                        if(label_ag == 0) {
                                            printf("\n\n_ Executando Algoritmo Genetico: Padrao\n");
                                            ++label_ag;
                                        }
                                    }
                                    else {
                                        if(label_ag_1 == 0) {
                                            printf("\n\n_ Executando Algoritmo Genetico: Com Mutacao por Troca\n");
                                            ++label_ag_1;
                                        }
                                    }
                                    
                                        
                                    // Faz um ciclo com o número de execuções definidas
                                    c1_exec = clock();
                                    for (r = 0; r < runs; r++) {
                                        // Geração da população inicial
                                        //pop = init_pop(EA_param); //TEST MODO ANTIGO
                                        if(r == 0)
                                            pop = init_pop(EA_param);
                                        else
                                            pop = attr_pop(EA_param, gen_best_ever.p, 0);
                                        
                                        if(conb != 0) 
                                            pop = attr_pop(EA_param, gen_best_ever_all_conb.p, EA_param.popsize - 1);
                                        
                                        //remove os elementos que possem distancias negativas da solucao inicial
                                        //for(v = 0; v < EA_param.popsize ; ++v)//TEST
                                        //    pop[v].p = remove_negative(mat, pop[v].p, n_elements);
                                        pop[0].p = remove_negative(mat, pop[0].p, n_elements);

                                        
                                        // Avalia a população inicial
                                        evaluate_sol(pop, EA_param, mat);
                                        gen_actual = 1;
                                        gen_best_run = pop[0];
                                        gen_best_run = get_best(pop, EA_param, gen_best_run);
                                        parents = malloc(sizeof(chrom) * EA_param.popsize);
                                        if (parents==NULL) {
                                            printf("Erro na alocacao de memoria: execucao ag genetico, definicao dos parents.\n");
                                            exit(1);
                                        }
                                        // Ciclo de optimização
                                        while (gen_actual <= EA_param.numGenerations)
                                        {
                                            // Torneio binário para encontrar os progenitores (ficam armazenados no vector parents)
                                            tournament(pop, EA_param, parents);
                                            // Aplica os operadores genéticos aos pais (os descendentes ficam armazenados na estrutura pop)
                                            genetic_operators(parents, EA_param, pop);
                                            
                                            //torna a eliminar os negativos
                                            //for(v = 0; v < EA_param.popsize ; ++v) //TEST Verificar se ajuda mesmo ou nao
                                            //    pop[v].p = remove_negative(mat, pop[v].p, n_elements);
                                            
                                            // Avalia a nova população (a dos filhos)
                                            evaluate_sol(pop, EA_param, mat);
                                            // Actualiza a melhor solução encontrada
                                            gen_best_run = get_best(pop, EA_param, gen_best_run);
                                            gen_actual++;
                                        }
                                        
                                        // Escreve resultados da repetição que terminou
                                        //printf("\n------- Repeticao | %d | -------\n", r+1);
                                        //write_best(gen_best_run, EA_param);
                                        gen_mbf += gen_best_run.fitness;
                                        if (r == 0 || gen_best_run.fitness > gen_best_ever.fitness)
                                            gen_best_ever = gen_best_run;
                                        // Liberta a memória usada
                                        free(parents);
                                        free(pop);
                                        ++exec_gen;
                                        ++exec_total; //incrementa o contador total de execuções
                                    }
                                    c2_exec = clock();
                                    time_exec = (c2_exec - c1_exec)*1000/CLOCKS_PER_SEC;
                                    if (conb == 0 || gen_best_ever.fitness > gen_best_ever_all_conb.fitness) { 
                                        gen_best_ever_all_conb = gen_best_ever;
                                        best_conb_parm = EA_param;
                                        gen_best_ever_all_conb.time_exec = time_exec;
                                    }
                                    ++conb;
                                    printf("\n-------   Combinacao %d --------\n", conb);
                                    // Escreve resultados globais
                                    printf("\n\nMedia das Melhores Solucoes Encontradas: %.2f", gen_mbf / r);
                                    printf("\nMelhor solucao encontrada: ");
                                    write_best(gen_best_ever, EA_param);
                                    printf("\nTempo utilizado na combinacao: %0.f ms", time_exec);
                                }
                                
                                printf("\n-------   Fim das Combinacoes  --------\n");
                                // Escreve resultados globais
                                printf("\nMelhor solucao encontrada de todas as combinacoes: ");
                                write_best(gen_best_ever_all_conb, best_conb_parm);
                                printf("> Tempo utilizado pela melhor solucao: %0.f ms\n\n", gen_best_ever_all_conb.time_exec);
                                
        }
        
        //executa os algoritmos locais
        if(strcmp(algoritmo_exec, "--all") == 0 || strcmp(algoritmo_exec, "--loc") == 0) {
            label_ag = label_ag_1 = 0;
            best = malloc(sizeof(int) *n_elements);
            sol = malloc(sizeof(int) *n_elements);
            best_all_cbn = malloc(sizeof(int) *n_elements);
            
            if(sol == NULL || best == NULL){
                printf("Erro na alocacao de memoria: execucao do algoritmo local.");
                exit(1);
            }
            
            //Loop de execucão para os parametros: it, prob, recrist, t_max, t_min;
            //dados ja foram iniciados estao em : mat
            
            conb = 0;
            int label_ag_2 = 0;
            label_ag = label_ag_1 = 0;
            best_time_exec = 0;
            int mod_recrist = 0;
            int best_mod_recrist = 0;
            for(b = 0; b < recrist.size; ++b) {//trepa_colinas_recristalizacao_simulada
                for(a = 0; a < it.size; ++a) {  //it
                    for(c = 0; c < prob.size; ++c)
                        for(d = 0; d < t_max.size; ++d)
                            for(e = 0; e < t_min.size; ++e) {
                                //gera_sol_inicial(sol, n_elements);
                                //sol = remove_negative(mat, sol, n_elements); 
                                //TODO: ELEMINAR ELEMENTOS COM DISTANCIA NEGATIVAS
                                //escreve_sol(sol,n_elements);
                                numitera = (int) parm_get(&it, a); //TEST
                                temp_max = (double) parm_get(&t_max, d);
                                temp_min = (double) parm_get(&t_min, e);
                                percent_prob = parm_get(&prob, c);
                                mod_recrist = (int)parm_get(&recrist, b);
                                loc_mbf = 0;
                                c1_exec = clock();
                                for (r = 0; r < runs; r++) {
                                    
                                    gera_sol_inicial(sol, n_elements); //TEST pega a melhor solucao da ultima execucao
                                    sol = remove_negative(mat, sol, n_elements); 
                                    
                                    if(mod_recrist == 0) {
                                        if(label_ag == 0) {
                                            printf("_ Executando Algoritmo Local: Trepa Colinas\n");
                                            ++label_ag;
                                        }
                                        custo = trepa_colinas(sol, mat, n_elements, numitera);
                                    }
                                    else if (mod_recrist == 1) {
                                        if(label_ag_1 == 0) {
                                            printf("_ Executando Algoritmo Local: Trepa Colinas Com Recristalizacao Simulada\n");
                                            ++label_ag_1;
                                        }
                                        
                                        custo = trepa_colinas_recristalizacao_simulada(sol, mat, n_elements, numitera, percent_prob, temp_min, temp_max);
                                    }
                                    else { // mod_recrist = 2
                                        if(label_ag_2 == 0) {
                                            printf("_ Executando Algoritmo Local: Trepa Colinas Probabilitico\n");
                                            ++label_ag_2;
                                        }

                                        custo = trepa_colinas_probabilistico(sol, mat, n_elements, numitera, percent_prob);
                                    }
                                    
                                    //printf("\n------- Repeticao | %d | -------\n", r+1);
                                    //escreve_sol(sol, n_elements);
                                    //printf("Custo final: %.2f\n", custo);
                                    loc_mbf+= custo;
                                    if(r == 0 || best_custo < custo)//maximizar custo
                                    {
                                        best_custo = custo;
                                        substitui(best, sol, n_elements);
                                    }
                                    ++exec_loc; 
                                    ++exec_total; //incrementa o contador total de execuções
                                }
                                c2_exec = clock();
                                time_exec = (c2_exec - c1_exec)*1000/CLOCKS_PER_SEC;
                                
                                
                                if (conb == 0 || best_custo > best_custo_all_conb) { 
                                    substitui(best_all_cbn, best, n_elements); 
                                    index_best_a = a;
                                    index_best_b = b;
                                    index_best_c = c;
                                    index_best_d = d;
                                    index_best_e = e;
                                    best_custo_all_conb = best_custo;
                                    best_time_exec = time_exec;
                                    best_mod_recrist = mod_recrist;
                                }
                                ++conb;
                                printf("\n-------   Combinacao %d --------\n", conb);
                                // Escreve resultados globais
                                
                                printf("\n\nMedia das Melhores Solucoes Encontradas: %.2f", loc_mbf / r);
                                 if(mod_recrist == 0){
                                    printf("\n> Tipo de Algoritmo Utilizado: Trepa Colinas");}
                                else
                                    printf("\n> Tipo de Algoritmo Utilizado: Trepa Colinas Com Recristalizacao Simulada");
                                
                                printf("\nMelhor solucao encontrada: ");
                                printf("\n> Conjunto M: ");
                                escreve_sol(best, n_elements);
                                printf("> Numero Elementos Seleccionados: %d", get_num_elements_sol(best, n_elements));
                                
                                printf("\nCusto final: %.2f", best_custo);
                                printf("\nTempo utilizado na combinacao: %0.f ms\n\n", time_exec);
                                
                                if(mod_recrist == 0) 
                                    break;
                            }
                }
            } ///fim das combinacoes
            printf("\n-------   Fim das Combinacoes  --------\n");
            // Escreve resultados globais
            printf("\nMelhor solucao encontrada de todas as combinacoes: ");
            printf("\nCusto final: %.2f", best_custo_all_conb);
            
            best_mod_recrist = (int) parm_get(&recrist, index_best_b);
            if(best_mod_recrist == 0)
                printf("\n> Tipo de Algoritmo Utilizado: Trepa Colinas");
            else
                printf("\n> Tipo de Algoritmo Utilizado: Trepa Colinas Com Recristalizacao Simulada");
            
            printf("\n> Conjunto M: ");
            escreve_sol(best_all_cbn, n_elements);
            
            printf("\n> Numero Elementos Seleccionados: %d", get_num_elements_sol(best_all_cbn, n_elements));
            
            printf("\n> Parametros utilizados:");
            printf("\n>> ITERACOES: %d", (int) parm_get(&it, index_best_a));
            
            printf("\n>> PROB: %.2f", parm_get(&prob, index_best_c));
            printf("\n>> T_MIN: %.2f", parm_get(&t_min, index_best_e));
            printf("\n>> T_MAX: %.2f", parm_get(&t_max, index_best_d));
            
            putchar('\n');
            
            printf("> Tempo utilizado pela melhor solucao: %0.f ms\n", best_time_exec);
            
            free(sol);
            free(best);
        }//fim dos algoritmos locais
        
        
        
        //executa os algoritmos hibridos
        if(strcmp(algoritmo_exec, "--all") == 0 || strcmp(algoritmo_exec, "--hib") == 0) {
            
            
            // Preenche a matriz com dados dos objectos (peso e valor) e a estrutura EA_param que foram definidos no ficheiro de input
            
            int hib_adv_mod = (int) parm_get(&p_hib_adv_mod, 0);
            int index_best_h;
            
            label_ag = 0; 
            label_ag_1 = 0;
            conb = 0;
            //Loop de execucão para os parametros: pm, ppop, pr, t_size, max_gen;
            //for(f = 0; f < recrist.size; ++f) //TODO
            //for(g = 0; g < percent_prob.size; ++f) //TODO
            for(k = 0; k < p_mutacao_troca.size; ++k) //Algoritmo Genetico com Mutacao por troca ou nao
                for(h = 0; h < it.size; ++h)
                    for(a = 0; a < t_size.size; ++a) //t_size
                        for(b = 0; b < max_gen.size; ++b) //max_gen
                            for(c = 0; c < ppop.size; ++c)//pop
                                for(d = 0; d < pm.size; ++d)//pm
                                    for(e = 0; e < pr.size; ++e) {//pr
                                        EA_param.tsize = (int) parm_get(&t_size, a);
                                        EA_param.popsize = (int) parm_get(&ppop, c);
                                        //EA_param.numGenerations = (int) parm_get(&max_gen, b);
                                        EA_param.numGenerations  = (int) (100 * parm_get(&max_gen, b) )/ EA_param.popsize;
                                        EA_param.pm = parm_get(&pm, d);
                                        EA_param.pr =  parm_get(&pr, e);
                                        EA_param.numElements = n_elements;
                                        EA_param.ro =  0.0;
                                        EA_param.mutacao_troca =  (int) parm_get(&p_mutacao_troca, k);
                                        gen_mbf = 0; //zerando a media das escolhidas
                                        // Faz um ciclo com o número de execuções definidas
                                        
                                        
                                        if(EA_param.mutacao_troca == 0) {
                                            if(label_ag == 0) {
                                                printf("\n\n_ Executando Algoritmo Hibrido:\n_ Ag Genteico Padrao  + Trepa Colinas com Recristalizacao\n");
                                                if(hib_adv_mod == 0)
                                                    printf("\t[INICIO]\n");
                                                else
                                                    printf("\t[INICIO - MEIO - FIM]\n");
                                                ++label_ag;
                                            }
                                        }
                                        else {
                                            if(label_ag_1 == 0) {
                                                printf("\n\n_ Executando Algoritmo Hibrido:\n_ Ag Genteico  Com Mutacao por Troca  + Trepa Colinas com Recristalizacao\n");
                                                if(hib_adv_mod == 0)
                                                    printf("\t[INICIO]\n");
                                                else
                                                    printf("\t[INICIO - MEIO - FIM]\n");
                                                ++label_ag_1;
                                            }
                                        }
                                        
                                        c1_exec = clock();
                                        for (r = 0; r < runs; r++) {
                                            // Geração da população inicial
                                            //pop = init_pop(EA_param);
                                            if(r == 0) //TEST
                                                pop = init_pop(EA_param);
                                            else
                                                pop = attr_pop(EA_param, gen_best_ever.p, 0);
                                            
                                            if(conb != 0) 
                                                pop = attr_pop(EA_param, gen_best_ever_all_conb.p, EA_param.popsize - 1);
                                            
                                            numitera = (int) parm_get(&it, h); //TEST
                                            
                                            //remove os elementos que possem distancias negativas da solucao inicial
                                            //for(v = 0; v < EA_param.popsize ; ++v) {
                                            pop[0].p = remove_negative(mat, pop[0].p, n_elements);
                                            //trepa_colinas(pop[v].p, mat, n_elements, numitera);
                                            //}
                                            
                                            //Hibrido NO INICIO apenas em uma populacao 
                                            trepa_colinas(pop[0].p, mat, n_elements, numitera);
                                            
                                            // Avalia a população inicial
                                            evaluate_sol(pop, EA_param, mat);
                                            gen_actual = 1;
                                            gen_best_run = pop[0];
                                            gen_best_run = get_best(pop, EA_param, gen_best_run);
                                            parents = malloc(sizeof(chrom) * EA_param.popsize);
                                            if (parents==NULL) {
                                                printf("Erro na alocacao de memoria: execucao ag genetico, definicao dos parents.\n");
                                                exit(1);
                                            }
                                            // Ciclo de optimização
                                            while (gen_actual <= EA_param.numGenerations)
                                            {
                                                //Hibrido NO MEIO
                                                if(hib_adv_mod == 1 )
                                                    trepa_colinas_recristalizacao_simulada(pop[0].p, mat, n_elements, numitera, 0.01, 0.05, 1);
                                                
                                                // Torneio binário para encontrar os progenitores (ficam armazenados no vector parents)
                                                tournament(pop, EA_param, parents);
                                                // Aplica os operadores genéticos aos pais (os descendentes ficam armazenados na estrutura pop)
                                                genetic_operators(parents, EA_param, pop);
                                                
                                                //torna a eliminar os negativos
                                                //for(v = 0; v < EA_param.popsize ; ++v) //TEST Verificar se ajuda mesmo ou nao
                                                //pop[v].p = remove_negative(mat, pop[v].p, n_elements);
                                                
                                                //Hibrido NO FIM
                                                if(hib_adv_mod == 1 )
                                                    trepa_colinas(pop[0].p, mat, n_elements, numitera);
                                                
                                                // Avalia a nova população (a dos filhos)
                                                evaluate_sol(pop, EA_param, mat);
                                                // Actualiza a melhor solução encontrada
                                                gen_best_run = get_best(pop, EA_param, gen_best_run);
                                                gen_actual++;
                                            }
                                            
                                            // Escreve resultados da repetição que terminou
                                            //printf("\n------- Repeticao | %d | -------\n", r+1);
                                            //write_best(gen_best_run, EA_param);
                                            gen_mbf += gen_best_run.fitness;
                                            if (r == 0 || gen_best_run.fitness > gen_best_ever.fitness)
                                                gen_best_ever = gen_best_run;
                                            // Liberta a memória usada
                                            free(parents);
                                            free(pop);
                                            ++exec_hib;
                                            ++exec_total; //incrementa o contador total de execuções
                                        }
                                        c2_exec = clock();
                                        time_exec = (c2_exec - c1_exec)*1000/CLOCKS_PER_SEC;
                                        
                                        if (conb == 0 || gen_best_ever.fitness > gen_best_ever_all_conb.fitness) { 
                                            gen_best_ever_all_conb = gen_best_ever;
                                            best_conb_parm = EA_param;
                                            gen_best_ever_all_conb.time_exec = time_exec;
                                            index_best_h = h;
                                            
                                        }
                                        ++conb;
                                        printf("\n-------   Combinacao %d --------\n", conb);
                                        // Escreve resultados globais
                                        printf("\n\nMedia das Melhores Solucoes Encontradas: %.2f", gen_mbf / r);
                                        printf("\nMelhor solucao encontrada: ");
                                        write_best(gen_best_ever, EA_param);
                                        printf(">> ITERACOES: %d\n", (int) parm_get(&it, h));
                                        printf("\nTempo utilizado na combinacao: %0.f ms", time_exec);
                                    }
                                    
                                    printf("\n-------   Fim das Combinacoes  --------\n");
                                    // Escreve resultados globais
                                    printf("\nMelhor solucao encontrada de todas as combinacoes: ");
                                    write_best(gen_best_ever_all_conb, best_conb_parm);
                                    printf("\n> Tipo de Algoritmo (Local) Utilizado: Trepa Colinas Com Recristalizacao Simulada");
                                    printf("\n>> ITERACOES: %d\n", (int) parm_get(&it, index_best_h));
                                    //printf("\n>> RECRISTALIZACAO: %d", (int) parm_get(&recrist, index_best_b));
                                    //printf("\n>> PROB: %.2f", parm_get(&prob, index_best_c));
                                    //printf("\n>> T_MIN: %.2f", parm_get(&t_min, index_best_e));
                                    //printf("\n>> T_MAX: %.2f", parm_get(&t_max, index_best_d));
                                    printf("> Tempo utilizado pela melhor solucao: %0.f ms\n", gen_best_ever_all_conb.time_exec);
                                    
        }
        //ao final da execucao libera o espaco de memoria utilizado pelo ficheiro que foi executado
        mat = release_matrix(n_elements, n_elements, mat);
        
        ++index;
    }  while(index < 12);
    
    printf("\n# Numero total de execucoes: %d\n", exec_total);
    printf("# Numero total de execucoes Gen: %d\n", exec_gen);
    printf("# Numero total de execucoes Hib: %d\n", exec_hib);
    printf("# Numero total de execucoes Loc: %d\n", exec_loc);
    c2 = clock();
    time = (c2 - c1)*1000/CLOCKS_PER_SEC;
    
    printf("\n# Tempo total de Execucao: %.0f ms\n", time);
    return 0;
}
