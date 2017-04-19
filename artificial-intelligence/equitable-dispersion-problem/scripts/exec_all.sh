#!/bin/bash
DIR_OUTPUT=outputs/
DIR_TIPO_1=outputs/tipo1/
DIR_TIPO_2=outputs/tipo2/
DIR_GEN=gen/
DIR_LOC=loc/
DIR_HIB=hib/


echo "# Iniciando Execuções: "


echo "#Algoritmo Local: "
echo "#> Ficheiros Tipo 1"
./bin/main tipo1/MDPI1_20.txt --loc > $DIR_TIPO_1$DIR_LOC"MDPI1_20_all_auto.txt"
    echo "#> Ficheiro tipo1/MDPI1_150.txt executado."
./bin/main tipo1/MDPI1_150.txt --loc > $DIR_TIPO_1$DIR_LOC"MDPI1_150_all_auto.txt"
     echo "#> Ficheiro tipo1/MDPI1_150.txt executado."
./bin/main tipo1/MDPI1_500.txt --loc > $DIR_TIPO_1$DIR_LOC"MDPI1_500_all_auto.txt"
     echo "#> Ficheiro tipo1/MDPI1_500.txt executado."
./bin/main tipo1/MDPI2_25.txt --loc > $DIR_TIPO_1$DIR_LOC"MDPI2_25_all_auto.txt"
     echo "#> Ficheiro tipo1/MDPI2_25.txt executado."
./bin/main tipo1/MDPI2_30.txt --loc > $DIR_TIPO_1$DIR_LOC"MDPI2_30_all_auto.txt"
     echo "#> Ficheiro tipo1/MDPI2_30.txt executado."
./bin/main tipo1/MDPI2_150.txt --loc > $DIR_TIPO_1$DIR_LOC"MDPI2_30_all_auto.txt"


echo "#> Ficheiros Tipo 2"
./bin/main tipo2/MDPII1_20.txt --loc > $DIR_TIPO_2$DIR_LOC"MDPII1_20_all_auto.txt"
     echo "#> Ficheiro tipo2/MDPII1_20.txt executado."
./bin/main tipo2/MDPII1_150.txt --loc > $DIR_TIPO_2$DIR_LOC"MDPII1_150_all_auto.txt"
     echo "#> Ficheiro tipo2/MDPII1_150.txt executado."
./bin/main tipo2/MDPII2_25.txt --loc > $DIR_TIPO_2$DIR_LOC"MDPII2_25_all_auto.txt"
     echo "#> Ficheiro tipo2/MDPII2_25.txt executado."
./bin/main tipo2/MDPII2_30.txt --loc > $DIR_TIPO_2$DIR_LOC"MDPII2_30_all_auto.txt"
     echo "#> Ficheiro tipo2/MDPII2_30.txt executado."
./bin/main tipo2/MDPII2_150.txt --loc > $DIR_TIPO_2$DIR_LOC"MDPII2_150_all_auto.txt"
     echo "#> Ficheiro tipo2/MDPII2_150.txt executado."
./bin/main tipo2/MDPII2_500.txt --loc > $DIR_TIPO_2$DIR_LOC"MDPII2_500_all_auto.txt"

echo "#Algoritmo Genético "
echo "#> Ficheiros Tipo 1"
./bin/main tipo1/MDPI1_20.txt --gen > $DIR_TIPO_1$DIR_GEN"MPI1_20_all_auto.txt"
./bin/main tipo1/MDPI1_150.txt --gen > $DIR_TIPO_1$DIR_GEN"MPI1_150_all_auto.txt"
./bin/main tipo1/MDPI1_500.txt --gen > $DIR_TIPO_1$DIR_GEN"MPI1_500_all_auto.txt"
./bin/main tipo1/MDPI2_25.txt --gen > $DIR_TIPO_1$DIR_GEN"MPI2_25_all_auto.txt"
./bin/main tipo1/MDPI2_30.txt --gen > $DIR_TIPO_1$DIR_GEN"MPI2_30_all_auto.txt"
./bin/main tipo1/MDPI2_150.txt --gen > $DIR_TIPO_1$DIR_GEN"MPI2_30_all_auto.txt"
echo "#> Ficheiros Tipo 2"
./bin/main tipo2/MDPII1_20.txt --gen > $DIR_TIPO_2$DIR_GEN"MPII1_20_all_auto.txt"
./bin/main tipo2/MDPII1_150.txt --gen > $DIR_TIPO_2$DIR_GEN"MPII1_150_all_auto.txt"
./bin/main tipo2/MDPII2_25.txt --gen > $DIR_TIPO_2$DIR_GEN"MPII2_25_all_auto.txt"
./bin/main tipo2/MDPII2_30.txt --gen > $DIR_TIPO_2$DIR_GEN"MPII2_30_all_auto.txt"
./bin/main tipo2/MDPII2_150.txt --gen > $DIR_TIPO_2$DIR_GEN"MPII2_150_all_auto.txt"
./bin/main tipo2/MDPII2_500.txt --gen > $DIR_TIPO_2$DIR_GEN"MPII2_500_all_auto.txt"

echo "#Algoritmo Hibrido "
echo "#> Ficheiros Tipo 1"
./bin/main tipo1/MDPI1_20.txt --hib > $DIR_TIPO_1$DIR_GEN"MPI1_20_all_auto.txt"
./bin/main tipo1/MDPI1_150.txt --hib > $DIR_TIPO_1$DIR_GEN"MPI1_150_all_auto.txt"
./bin/main tipo1/MDPI1_500.txt --hib > $DIR_TIPO_1$DIR_GEN"MPI1_500_all_auto.txt"
./bin/main tipo1/MDPI2_25.txt --hib > $DIR_TIPO_1$DIR_GEN"MPI2_25_all_auto.txt"
./bin/main tipo1/MDPI2_30.txt --hib > $DIR_TIPO_1$DIR_GEN"MPI2_30_all_auto.txt"
./bin/main tipo1/MDPI2_150.txt --hib > $DIR_TIPO_1$DIR_GEN"MPI2_30_all_auto.txt"
echo "#> Ficheiros Tipo 2"
./bin/main tipo2/MDPII1_20.txt --hib > $DIR_TIPO_2$DIR_HIB"MPII1_20_all_auto.txt"
./bin/main tipo2/MDPII1_150.txt --hib > $DIR_TIPO_2$DIR_HIB"MPII1_150_all_auto.txt"
./bin/main tipo2/MDPII2_25.txt --hib > $DIR_TIPO_2$DIR_HIB"MPII2_25_all_auto.txt"
./bin/main tipo2/MDPII2_30.txt --hib > $DIR_TIPO_2$DIR_HIB"MPII2_30_all_auto.txt"
./bin/main tipo2/MDPII2_150.txt --hib > $DIR_TIPO_2$DIR_HIB"MPII2_150_all_auto.txt"
./bin/main tipo2/MDPII2_500.txt --hib > $DIR_TIPO_2$DIR_HIB"MPII2_500_all_auto.txt"


echo "# Fim das Execuções!"

