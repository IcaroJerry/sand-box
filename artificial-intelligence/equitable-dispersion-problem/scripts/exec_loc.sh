#!/bin/bash
DIR_OUTPUT=outputs/
DIR_TIPO_1=tipo1/
DIR_TIPO_2=tipo2/
DIR_LOC=loc/

DIR_TIPO_1=$DIR_OUTPUT$DIR_TIPO_1
DIR_TIPO_2=$DIR_OUTPUT$DIR_TIPO_2

VERI_DIR=`ls -l $DIR_OUTPUT`
if [ $? = 2 ]; then
    mkdir $DIR_OUTPUT
    echo " Pasta $DIR_OUTPUT criada..."
fi

VERI_DIR=`ls -l $DIR_TIPO_1`
if [ $? = 2 ]; then
    mkdir $DIR_TIPO_1
    echo " Pasta $DIR_TIPO_1 criada..."
fi

VERI_DIR=`ls -l $DIR_TIPO_2`
if [ $? = 2 ]; then
    mkdir $DIR_TIPO_2
    echo " Pasta $DIR_TIPO_2 criada..."
fi

VERI_DIR=`ls -l $DIR_TIPO_1$DIR_LOC`
if [ $? = 2 ]; then
    mkdir $DIR_TIPO_1$DIR_LOC
    echo " Pasta $DIR_TIPO_1$DIR_LOC criada..."
fi

VERI_DIR=`ls -l $DIR_TIPO_2$DIR_LOC`
if [ $? = 2 ]; then
    mkdir $DIR_TIPO_2$DIR_LOC
    echo " Pasta $DIR_TIPO_2$DIR_LOC criada..."
fi



echo "# Iniciando Execuções: "

echo "#Algoritmo Local: "
echo "#> Ficheiros Tipo 1"
./bin/main tipo1/MDPI1_20.txt --loc > $DIR_TIPO_1$DIR_LOC/MDPI1_20_all_auto.txt
./bin/main tipo1/MDPI1_150.txt --loc > $DIR_TIPO_1$DIR_LOC/MDPI1_150_all_auto.txt
./bin/main tipo1/MDPI1_500.txt --loc > $DIR_TIPO_1$DIR_LOC/MDPI1_500_all_auto.txt
./bin/main tipo1/MDPI2_25.txt --loc > $DIR_TIPO_1$DIR_LOC/MDPI2_25_all_auto.txt
./bin/main tipo1/MDPI2_30.txt --loc > $DIR_TIPO_1$DIR_LOC/MDPI2_30_all_auto.txt
./bin/main tipo1/MDPI2_150.txt --loc > $DIR_TIPO_1$DIR_LOC/MDPI2_30_all_auto.txt
echo "#> Ficheiros Tipo 2"
./bin/main tipo2/MDPII1_20.txt --loc > $DIR_TIPO_2$DIR_LOC/MDPII1_20_all_auto.txt
./bin/main tipo2/MDPII1_150.txt --loc > $DIR_TIPO_2$DIR_LOC/MDPII1_150_all_auto.txt
./bin/main tipo2/MDPII2_25.txt --loc > $DIR_TIPO_2$DIR_LOC/MDPII2_25_all_auto.txt
./bin/main tipo2/MDPII2_30.txt --loc > $DIR_TIPO_2$DIR_LOC/MDPII2_30_all_auto.txt
./bin/main tipo2/MDPII2_150.txt --loc > $DIR_TIPO_2$DIR_LOC/MDPII2_150_all_auto.txt
./bin/main tipo2/MDPII2_500.txt --loc > $DIR_TIPO_2$DIR_LOC/MDPII2_500_all_auto.txt

echo "# Fim das Execuções!"
