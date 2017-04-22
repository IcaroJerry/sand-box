breed[lideres lider]
breed[sociais social]
breed[solitarios solitario]

turtles-own [energia tmpVida]
sociais-own [nv_socializacao]
lideres-own [nv_socializacao]

globals [nSol2Soc nSoc2Lid nLid2Soc
         nAfastamentos nContatos nLidContatos
         nv_perda_ganho nv_alcance_ags
         tmp_vida_solitarios tmp_vida_sociais tmp_vida_lideres
         nSolMortos nSocMortos nLidMortos
        ]

to setup
  clear-all
  check-configs
  setup-pacthes
  setup-turtles
  reset-ticks
end

to go
  if ((ticks = nTicks) or (count turtles = 0))
  [
    atualizar-dados-restantes ;; Antes de parar, atualiza variavéis com os agentes restantes
    stop
  ]
  check-death
  move-solitarios
  check-sociais ;;Garante o nv de Sociabilidade o Max de 100%
  move-sociais
  move-lideres
  tick
end

to atualizar-dados-restantes
  ask turtles ;;solicita os agentes que ficaram vivos
  [
    ifelse (breed = solitarios)
    [
      set tmp_vida_solitarios (tmp_vida_solitarios + tmpVida) ;; soma seu tempo de vida
      ;;estima a quantidade de iterações média que ele poderia ter caso o programa continuasse
      set tmp_vida_solitarios (tmp_vida_solitarios + (energia / 1.5)) ;; divide a energia atual por 1,5 pois é o gasto médio por iteração
    ]
    [
      ifelse (breed = sociais)
      [
        set tmp_vida_sociais (tmp_vida_sociais + tmpVida)
        set tmp_vida_sociais (tmp_vida_sociais + energia) ;;estima o tempo de vida
      ]
      ;;lideres
      [
        set tmp_vida_lideres (tmp_vida_lideres + tmpVida)
        set tmp_vida_lideres (tmp_vida_lideres + energia) ;;estima o tempo de vida
      ]
    ]
  ]
end

to setup-pacthes
  ask patches [if random 100 < taxa_bonus [set pcolor blue]] ;; BONUS
  ask patches [if random 100 < taxa_alimentos [set pcolor green]];; ALIMENTO
  ask one-of patches with [pcolor = black][set pcolor yellow] ;; CELULA ESPECIAL
end

to setup-turtles
  create-sociais qnt_ag_sociais
  [
    set nv_socializacao 10 ;; 10%
    set color orange
    set shape "face happy"
  ]
  create-solitarios qnt_ag_solitarios
  [
    set color red
    set shape "face sad"
  ]
  ask turtles
  [
    set heading 0 ;; Todos apontam para o Norte!
    set energia energia_inicial_ags
    setxy random-xcor random-ycor
    while [ pcolor != black] ;; Posiciona os agentes apenas em áreas vazias
      [setxy random-xcor random-ycor]
  ]
end

to check-configs
  ifelse(taxa_perda_ganho = "normal")
  [set nv_perda_ganho 1 ]
  [
    ifelse(taxa_perda_ganho = "médio")
    [set nv_perda_ganho 2 ]
    [set nv_perda_ganho 3 ];;alta
  ]

  ifelse( alcance_ags = "normal")
  [set nv_alcance_ags 1];;equivale a uma casa a frente
  ;;alto
  [set nv_alcance_ags 2] ;;equivale a duas casas a frente

  if(ComLider? = false)[set SeguirLider? false]

end
to check-death
  ask turtles
  [
    set energia energia - 1 ;; Cada interação os agentes perdem pelo menos 1 energias

    set tmpVida (tmpVida + 1)

    if (energia <= 0 );;Se o agente não tiver mais energia, morre.
    [
      ifelse (breed = solitarios)
      [
       set tmp_vida_solitarios (tmp_vida_solitarios + tmpVida)
       set nSolMortos (nSolMortos + 1)
      ]
      [
        ifelse (breed = sociais)
        [
          set tmp_vida_sociais (tmp_vida_sociais + tmpVida)
          set  nSocMortos (nSocMortos + 1)
        ]
        ;;lideres
        [
          set tmp_vida_lideres (tmp_vida_lideres + tmpVida)
          set  nLidMortos (nLidMortos + 1)
        ]
      ]
      die
    ]
        ;;Os agentes sociais morrem se nv de socializacao for menor que 1%
    if ((breed = sociais) and (nv_socializacao < 1));;1%
    [
      set  nSocMortos (nSocMortos + 1)
      die
    ]

  ]
  check-lider-isVivo?
end

to check-sociais
  ask sociais with [nv_socializacao > 100] ;;100%
  [set nv_socializacao 100]
end

to move-sociais
  ask sociais
  [
    ;;ALIMENTO na célula atual
    if ([pcolor] of patch-here = green)
    [
      set energia ( energia + energia_alimentos)
      ask patch-here [set pcolor black] ;; APAGA alimento
    ]

    ;;DOURADA na célula atual
    if([pcolor] of patch-here = yellow and ComLider? = true)
    [
      let nv_socia_tmp nv_socializacao
      let energia_tmp energia

      set breed lideres
      set tmpVida 0
      set nv_socializacao nv_socia_tmp
      set energia energia_tmp
      set shape "face neutral"
      set color yellow
      set nSoc2Lid nSoc2Lid + 1
      ask patch-here [set pcolor black] ;; APAGA dourada
      ;;dourada-posicao-randomica ;;permite mais de um lider
    ]

    ;;BONUS na célula atual
    if([pcolor] of patch-here = blue)
    [
      ;;Conceder bonus
      let bonus random 4
      ifelse (bonus = 1) ;; Aumenta a energia em 10%
      [
        set energia (energia + (energia * 0.10)) ;;10%
      ]
      [
        ifelse (bonus = 2) ;; Incrementa 1% nv socializacao
        [
          set nv_socializacao (nv_socializacao + 1) ;; 1%
        ]
        [
          ifelse (bonus = 3) ;; Decrementa 1% nv socializacao
          [
            set nv_socializacao (nv_socializacao - 1) ;; 1%
          ]
          [
            ;; Matar o agente caso seu nv socializacao seja inferior 50%
            if (nv_socializacao < 50) ;;50%
            [
              set  nSocMortos (nSocMortos + 1)
              die
            ]
          ]
        ]
      ]
      ask patch-here [set pcolor black] ;; APAGA azul
    ]

    ;;Percepcao a FRENTE-DIREITA-ESQUERDA: Verifica se tem algum agente
    if ((any? turtles-on patch-ahead nv_alcance_ags) or
        (any? turtles-on patch-right-and-ahead 90 nv_alcance_ags) or
        (any? turtles-on patch-left-and-ahead 90 nv_alcance_ags))
    [

      let qntAgVizinhancaSolitario 0
      let qntAgVizinhancaSocial 0

      set qntAgVizinhancaSolitario count (turtles-on patch-ahead nv_alcance_ags) with [breed = solitarios]
      set qntAgVizinhancaSolitario qntAgVizinhancaSolitario + count (turtles-on patch-right-and-ahead 90 nv_alcance_ags) with[breed = solitarios]
      set qntAgVizinhancaSolitario qntAgVizinhancaSolitario + count (turtles-on patch-left-and-ahead 90 nv_alcance_ags) with[breed = solitarios]

      set qntAgVizinhancaSocial count (turtles-on patch-ahead nv_alcance_ags) with[breed = sociais]
      set qntAgVizinhancaSocial qntAgVizinhancaSocial + count (turtles-on patch-right-and-ahead 90 nv_alcance_ags) with[breed = sociais]
      set qntAgVizinhancaSocial qntAgVizinhancaSocial + count (turtles-on patch-left-and-ahead 90 nv_alcance_ags) with[breed = sociais]

      set nContatos (nContatos + qntAgVizinhancaSolitario + qntAgVizinhancaSocial);; adicionando números de contatos

      ;;Se o agente encontrar mais de dois agentes SOCIAL na sua vizinhança
      if (qntAgVizinhancaSocial > 2)
      [set nv_socializacao (nv_socializacao + (2 * nv_perda_ganho))] ;;2%
      ;;Se o agente encontrar mais de dois agentes SOLITARIO na sua vizinhança
      if (qntAgVizinhancaSolitario > 2)
      [set nv_socializacao (nv_socializacao - (2 * nv_perda_ganho))] ;;2%
    ]

    ;Variavél que indica o movimento do agente
    let movimento -1

    ;;Decide qual movimento será executado
    ;;Movimento preferencial em direção a um agente
    if (Agrupar? = true)
    [
       if (any? turtles-on patch-ahead nv_alcance_ags) [set movimento 0]
       if (any? turtles-on patch-right-and-ahead 90 nv_alcance_ags) [set movimento 1]
       if (any? turtles-on patch-left-and-ahead 90 nv_alcance_ags) [set movimento 2]
    ]

    if(SeguirLider? = true) ;;Se seguir o Lider for prioridade
    [
      if (any? (turtles-on patch-ahead nv_alcance_ags) with [breed = lideres]) [set movimento 0]
      if (any? (turtles-on patch-right-and-ahead 90 nv_alcance_ags) with [breed = lideres]) [set movimento 1]
      if (any? (turtles-on patch-left-and-ahead 90 nv_alcance_ags) with [breed = lideres]) [set movimento 2]
    ]

    if (movimento < 0) [set movimento random 3] ;;Não encontrou nenhum agente

    ;;MOVIMENTA-SE
    ifelse movimento = 0
    [fd 1]
    [
      ifelse movimento = 1
      [rt 90] [lt 90]
    ]

    if (SempAvanc? = true and  movimento > 0) [fd 1]
  ]
end

to move-solitarios
  ask solitarios
  [
    ;;Percepcao simultanea FRENTE + DIREITA:
    ;;Verifica se tem agentes ao mesmo tempo a sua direita e na frente
    ifelse (( any? turtles-on patch-ahead nv_alcance_ags) and
            (any? turtles-on patch-right-and-ahead 90 nv_alcance_ags))
    [
      ;;set heading 180 ;;Aponta para o sul e recua
      ;;fd 1
      set nAfastamentos nAfastamentos + 1 ;; TESTE
      back 1
      lt 90
      fd 1
      set energia (energia - 3) ;; Perde 3 energias a mais em movimento especial
      set energia (energia + (energia * 0.05)) ;;Agente perde 5% da sua energia caso encontre mais de uma agente em sua vizinhaça
    ]
    [
      ;;Percepcao a FRENTE: Verifica se tem algum agente
      ifelse any? turtles-on patch-ahead nv_alcance_ags
      [
        ;;set heading 180 ;;Aponta para o sul e recua
        ;;fd 1
        back 1
        set nAfastamentos nAfastamentos + 1 ;; TESTE
        set energia (energia - 3) ;; Perde 3 energias a mais em movimento especial

        ;;Agente perde 5% da sua energia caso encontre mais de uma agente em sua vizinhança
        if (count (turtles-on patch-ahead nv_alcance_ags) > 1)
        [set energia (energia + (energia * 0.05)) ]
      ]
      [
        ;;Percepcao a DIREITA: Verifica se tem algum agente
        ifelse any? turtles-on patch-right-and-ahead 90 nv_alcance_ags
        [
          lt 90 ;;vira para sua esquerda e avanca
          fd 1
          set nAfastamentos nAfastamentos + 1 ;; TESTE
          set energia (energia - 3) ;; Perde 3 energias a mais em movimento especial

          ;;Agente perde 5% da sua energia caso encontre mais de uma agente em sua vizinhança
          if (count (turtles-on patch-ahead nv_alcance_ags) > 1)
          [set energia (energia + (energia * 0.05)) ]
        ]
        ;;Caso não encontre NENHUM agente à direita nem à esquerda
        [
          ;;ALIMENTO na célula atual
          if ([pcolor] of patch-here = green)
          [
            set energia ( energia + energia_alimentos)
            ask patch-here [set pcolor black] ;; APAGA alimento
          ]

          ;;DOURADA na célula atual
          if([pcolor] of patch-here = yellow)
          [
            let energia_tmp energia

            set breed sociais
            set energia energia_tmp
            set nv_socializacao 10 ;; 10%
            set color orange
            set shape "face happy"
            set nSol2Soc nSol2Soc + 1
            ask patch-here [set pcolor black] ;; APAGA dourada
            dourada-posicao-randomica
          ]

          ;;MOVIMENTA-SE
          let movimento random 1

          ifelse movimento =  0
          [fd 1] [rt 90]

          if (SempAvanc? = true and  movimento > 0) [fd 1];;Caso o agente tenha apenas virado e a variavel indique para sempre avançar uma casa, ele vira e avança
        ]
      ]
    ]
  ]
end

to move-lideres
    ask lideres
    [

    ;;Percepcao a FRENTE-DIREITA-ESQUERDA-ATRAS: Verifica se tem algum agente
    if ((any? turtles-on patch-ahead nv_alcance_ags) or
        (any? turtles-on patch-right-and-ahead 90 nv_alcance_ags) or
        (any? turtles-on patch-left-and-ahead 90 nv_alcance_ags) or
        (any? turtles-on patch-ahead  (-1 * nv_alcance_ags)))
    [
      let qntAgVizinhanca 0
      set qntAgVizinhanca count (turtles-on patch-ahead nv_alcance_ags)
      set qntAgVizinhanca qntAgVizinhanca + count (turtles-on patch-right-and-ahead 90 nv_alcance_ags)
      set qntAgVizinhanca qntAgVizinhanca + count (turtles-on patch-left-and-ahead 90 nv_alcance_ags)
      set qntAgVizinhanca qntAgVizinhanca + count (turtles-on patch-ahead (-1 * nv_alcance_ags))

      set nLidContatos (nLidContatos + qntAgVizinhanca) ;;Encontrou algum agente(s) em sua vizinhança
    ]

     let nv_socializacao_lider nv_socializacao
     let energia_capturada 0

     ;;Encontrou algum agente Social em sua vizinhança
     if (any? (turtles-on neighbors4) with [breed = sociais]);;  ou if any? (sociais-on neighbors4))
     [
      ask one-of (turtles-on neighbors4) with [breed = sociais] ;; Escolhe um dos agentes da vizinhança
      [
        ifelse(nv_socializacao_lider < 25)
        [
          set energia_capturada (energia * 0.2) ;;capturando 20 % energia
          set energia (energia - energia_capturada) ;;retirando do agente energia capturada
        ]
        [
          ifelse(nv_socializacao_lider > 24 and nv_socializacao_lider < 51)
          [
            set energia_capturada (energia * 0.5) ;;capturando 50 % energia
            set energia (energia - energia_capturada)
          ]
          ;;Se nv_socializacao_lider > 50
          [
            set energia_capturada energia
            set energia 0
            set  nSocMortos (nSocMortos + 1)
            die ;;mata o agente social
          ]
        ]
      ]
      if (energia_capturada > 0) ;; Caso tenha capturado alguma energia
      [set energia (energia + energia_capturada)] ;;Absorvendo a energia capturada
     ]

    ;;BONUS na célula atual
    if([pcolor] of patch-here = blue)
    [
      ;;Conceder bonus
      let bonus random 3
      ifelse (bonus = 1) ;; Volta a ser apenas um Social
      [
        let nv_soc_temp nv_socializacao
        let energia_temp energia

        set tmp_vida_lideres (tmp_vida_lideres + tmpVida)
        set breed sociais
        set nv_socializacao nv_soc_temp ;; Atribui a energia que já tinha
        set energia energia_temp
        set color orange
        set shape "face happy"
        set nLid2Soc nLid2Soc + 1
        set tmpVida 0

        ;;dourada-posicao-randomica
      ]
      [
        ifelse (bonus = 2) ;; Incrementa 5% nv socializacao
        [
          set nv_socializacao (nv_socializacao + (5 * nv_perda_ganho)) ;; 5%
        ]
        [
          if (bonus = 3) ;; Decrementa 1% nv socializacao
          [
            set nv_socializacao (nv_socializacao - (5 * nv_perda_ganho)) ;; 5%
          ]
        ]
      ]
      ask patch-here [set pcolor black] ;; APAGA azul
    ]


    ;Variavél que indica o movimento do agente
    let movimento -1

    ;;Decide qual movimento será executado
    ;;Movimento preferencial em direção a um agente
    if (Agrupar? = true)
    [
       if (any? turtles-on patch-ahead nv_alcance_ags) [set movimento 0]
       if (any? turtles-on patch-right-and-ahead 90 nv_alcance_ags) [set movimento 1]
       if (any? turtles-on patch-left-and-ahead 90 nv_alcance_ags) [set movimento 2]
    ]

    if (movimento < 0) [set movimento random 3] ;;Não encontrou nenhum agente

    ;;MOVIMENTA-SE
    ifelse movimento = 0
    [fd 1]
    [
      ifelse movimento = 1
      [rt 90] [lt 90]
    ]

    if (SempAvanc? = true and  movimento > 0) [fd 1]
 ]
end

to dourada-posicao-randomica
  ask one-of patches with [pcolor = black][set pcolor yellow]
end

to check-lider-isVivo?
  if ((count (patches with [pcolor = yellow]) = 0) and (count lideres  = 0) and (ComLider? = true))
  [dourada-posicao-randomica]
end
@#$#@#$#@
GRAPHICS-WINDOW
188
10
679
522
18
18
13.0
1
10
1
1
1
0
1
1
1
-18
18
-18
18
0
0
1
ticks
30.0

BUTTON
3
11
69
44
NIL
Setup\n
NIL
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

SLIDER
5
51
181
84
taxa_bonus
taxa_bonus
0
15
0
5
1
%
HORIZONTAL

SLIDER
4
87
181
120
taxa_alimentos
taxa_alimentos
5
20
15
5
1
%
HORIZONTAL

SLIDER
4
121
181
154
energia_alimentos
energia_alimentos
12.5
50
50
12.5
1
NIL
HORIZONTAL

SWITCH
4
267
149
300
ComLider?
ComLider?
0
1
-1000

SLIDER
6
195
182
228
qnt_ag_sociais
qnt_ag_sociais
0
99
66
33
1
NIL
HORIZONTAL

SLIDER
4
232
184
265
qnt_ag_solitarios
qnt_ag_solitarios
0
99
66
33
1
NIL
HORIZONTAL

SLIDER
5
157
180
190
energia_inicial_ags
energia_inicial_ags
25
100
50
25
1
NIL
HORIZONTAL

BUTTON
83
10
152
43
Go!
go
T
1
T
OBSERVER
NIL
NIL
NIL
NIL
1

MONITOR
1082
10
1169
55
NIL
ticks
17
1
11

MONITOR
1081
115
1158
160
Sol. > Soc.
nSol2Soc
17
1
11

SWITCH
3
373
150
406
SempAvanc?
SempAvanc?
1
1
-1000

PLOT
685
10
914
130
Linha do tempo
ticks
qnt
0.0
1.0
0.0
1.0
true
true
"" ""
PENS
"Solitários" 1.0 0 -2674135 true "" "plot count solitarios"
"Sociais" 1.0 0 -955883 true "" "plot count sociais"
"Lideres" 1.0 0 -1184463 true "" "plot count lideres"

SLIDER
1081
60
1173
93
nTicks
nTicks
250
1000
500
250
1
NIL
HORIZONTAL

SWITCH
4
337
149
370
Agrupar?
Agrupar?
0
1
-1000

PLOT
917
258
1077
378
Lider
ticks
qnt
0.0
1.0
0.0
1.0
true
false
"" ""
PENS
"Lideres" 1.0 0 -1184463 true "" "plot count lideres"

MONITOR
1081
161
1159
206
Soc. > Lid.
nSoc2Lid
17
1
11

MONITOR
1080
207
1158
252
Lid. > Soc.
nLid2Soc
17
1
11

PLOT
918
10
1078
130
Alimen. & Bônus
ticks
qnt
0.0
1.0
0.0
1.0
true
false
"" ""
PENS
"Bônus" 1.0 0 -13345367 true "" "plot count patches with [pcolor = blue]"
"Alimentos" 1.0 0 -10899396 true "" "plot count patches with [pcolor = green]"

PLOT
682
383
908
521
Afastamentos/Contatos
ticks
qnt
0.0
1.0
0.0
1.0
true
false
"" ""
PENS
"Afastamentos" 1.0 0 -2674135 true "" "plot nAfastamentos"
"Contatos" 1.0 0 -955883 true "" "plot nContatos"
"Lid. Contatos" 1.0 0 -1184463 true "" "plot nLidContatos"

CHOOSER
7
477
150
522
taxa_perda_ganho
taxa_perda_ganho
"normal" "alta"
0

TEXTBOX
7
460
157
478
Nível de Sociabilização:
12
0.0
1

SWITCH
4
302
148
335
SeguirLider?
SeguirLider?
1
1
-1000

CHOOSER
5
409
143
454
alcance_ags
alcance_ags
"normal" "alto"
0

MONITOR
757
319
816
364
Sociais
tmp_vida_sociais / (qnt_ag_sociais + nSol2Soc + nLid2Soc - nSoc2Lid)
2
1
11

MONITOR
687
319
754
364
Solitários
tmp_vida_solitarios / (qnt_ag_solitarios - nSol2Soc)
2
1
11

TEXTBOX
686
301
836
319
Tempo Médio de Vida:
12
0.0
1

MONITOR
820
319
882
364
Líderes
tmp_vida_lideres / nSoc2Lid
2
1
11

MONITOR
913
410
1006
455
Afastamentos
nAfastamentos
17
1
11

MONITOR
1009
411
1081
456
Contatos
nContatos
17
1
11

MONITOR
1082
411
1165
456
Contat. (Líd.)
nLidContatos
17
1
11

MONITOR
914
476
1000
521
Afast./Ticks
nAfastamentos / ticks
2
1
11

MONITOR
1005
476
1080
521
Cont./Ticks
nContatos / ticks
2
1
11

MONITOR
1082
476
1166
521
Con(L)/Ticks
nLidContatos / ticks
2
1
11

TEXTBOX
914
394
1064
412
Total:
12
0.0
1

TEXTBOX
915
459
1065
477
Média:
12
0.0
1

TEXTBOX
686
139
836
157
Agentes Vivos:
12
0.0
1

MONITOR
684
157
750
202
Solitários
count solitarios
2
1
11

MONITOR
752
157
812
202
Sociais
count sociais
2
1
11

MONITOR
814
157
876
202
Líderes
count lideres
17
1
11

MONITOR
685
238
751
283
Solitários
nSolMortos
2
1
11

TEXTBOX
685
221
897
251
Número de mortes dos agentes:
12
0.0
1

MONITOR
752
238
812
283
Sociais
nSocMortos
2
1
11

MONITOR
815
238
877
283
Líderes
nLidMortos
2
1
11

PLOT
918
134
1078
254
Nº de Conversões
ticks
qnt
0.0
1.0
0.0
1.0
true
false
"" ""
PENS
"Sol2Soc" 1.0 0 -2674135 true "" "plot nSol2Soc"
"Soc2Lid" 1.0 0 -955883 true "" "plot nSoc2Lid"
"Lid2Soc" 1.0 0 -1184463 true "" "plot nLid2Soc"

@#$#@#$#@
## WHAT IS IT?

This section could give a general understanding of what the model is trying to show or explain.

## HOW IT WORKS

This section could explain what rules the agents use to create the overall behavior of the model.

## HOW TO USE IT

This section could explain how to use the model, including a description of each of the items in the interface tab.

## THINGS TO NOTICE

This section could give some ideas of things for the user to notice while running the model.

## THINGS TO TRY

This section could give some ideas of things for the user to try to do (move sliders, switches, etc.) with the model.

## EXTENDING THE MODEL

This section could give some ideas of things to add or change in the procedures tab to make the model more complicated, detailed, accurate, etc.

## NETLOGO FEATURES

This section could point out any especially interesting or unusual features of NetLogo that the model makes use of, particularly in the Procedures tab.  It might also point out places where workarounds were needed because of missing features.

## RELATED MODELS

This section could give the names of models in the NetLogo Models Library or elsewhere which are of related interest.

## CREDITS AND REFERENCES

This section could contain a reference to the model's URL on the web if it has one, as well as any other necessary credits or references.
@#$#@#$#@
default
true
0
Polygon -7500403 true true 150 5 40 250 150 205 260 250

airplane
true
0
Polygon -7500403 true true 150 0 135 15 120 60 120 105 15 165 15 195 120 180 135 240 105 270 120 285 150 270 180 285 210 270 165 240 180 180 285 195 285 165 180 105 180 60 165 15

arrow
true
0
Polygon -7500403 true true 150 0 0 150 105 150 105 293 195 293 195 150 300 150

box
false
0
Polygon -7500403 true true 150 285 285 225 285 75 150 135
Polygon -7500403 true true 150 135 15 75 150 15 285 75
Polygon -7500403 true true 15 75 15 225 150 285 150 135
Line -16777216 false 150 285 150 135
Line -16777216 false 150 135 15 75
Line -16777216 false 150 135 285 75

bug
true
0
Circle -7500403 true true 96 182 108
Circle -7500403 true true 110 127 80
Circle -7500403 true true 110 75 80
Line -7500403 true 150 100 80 30
Line -7500403 true 150 100 220 30

butterfly
true
0
Polygon -7500403 true true 150 165 209 199 225 225 225 255 195 270 165 255 150 240
Polygon -7500403 true true 150 165 89 198 75 225 75 255 105 270 135 255 150 240
Polygon -7500403 true true 139 148 100 105 55 90 25 90 10 105 10 135 25 180 40 195 85 194 139 163
Polygon -7500403 true true 162 150 200 105 245 90 275 90 290 105 290 135 275 180 260 195 215 195 162 165
Polygon -16777216 true false 150 255 135 225 120 150 135 120 150 105 165 120 180 150 165 225
Circle -16777216 true false 135 90 30
Line -16777216 false 150 105 195 60
Line -16777216 false 150 105 105 60

car
false
0
Polygon -7500403 true true 300 180 279 164 261 144 240 135 226 132 213 106 203 84 185 63 159 50 135 50 75 60 0 150 0 165 0 225 300 225 300 180
Circle -16777216 true false 180 180 90
Circle -16777216 true false 30 180 90
Polygon -16777216 true false 162 80 132 78 134 135 209 135 194 105 189 96 180 89
Circle -7500403 true true 47 195 58
Circle -7500403 true true 195 195 58

circle
false
0
Circle -7500403 true true 0 0 300

circle 2
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240

cow
false
0
Polygon -7500403 true true 200 193 197 249 179 249 177 196 166 187 140 189 93 191 78 179 72 211 49 209 48 181 37 149 25 120 25 89 45 72 103 84 179 75 198 76 252 64 272 81 293 103 285 121 255 121 242 118 224 167
Polygon -7500403 true true 73 210 86 251 62 249 48 208
Polygon -7500403 true true 25 114 16 195 9 204 23 213 25 200 39 123

cylinder
false
0
Circle -7500403 true true 0 0 300

dot
false
0
Circle -7500403 true true 90 90 120

face happy
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 255 90 239 62 213 47 191 67 179 90 203 109 218 150 225 192 218 210 203 227 181 251 194 236 217 212 240

face neutral
false
0
Circle -7500403 true true 8 7 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Rectangle -16777216 true false 60 195 240 225

face sad
false
0
Circle -7500403 true true 8 8 285
Circle -16777216 true false 60 75 60
Circle -16777216 true false 180 75 60
Polygon -16777216 true false 150 168 90 184 62 210 47 232 67 244 90 220 109 205 150 198 192 205 210 220 227 242 251 229 236 206 212 183

fish
false
0
Polygon -1 true false 44 131 21 87 15 86 0 120 15 150 0 180 13 214 20 212 45 166
Polygon -1 true false 135 195 119 235 95 218 76 210 46 204 60 165
Polygon -1 true false 75 45 83 77 71 103 86 114 166 78 135 60
Polygon -7500403 true true 30 136 151 77 226 81 280 119 292 146 292 160 287 170 270 195 195 210 151 212 30 166
Circle -16777216 true false 215 106 30

flag
false
0
Rectangle -7500403 true true 60 15 75 300
Polygon -7500403 true true 90 150 270 90 90 30
Line -7500403 true 75 135 90 135
Line -7500403 true 75 45 90 45

flower
false
0
Polygon -10899396 true false 135 120 165 165 180 210 180 240 150 300 165 300 195 240 195 195 165 135
Circle -7500403 true true 85 132 38
Circle -7500403 true true 130 147 38
Circle -7500403 true true 192 85 38
Circle -7500403 true true 85 40 38
Circle -7500403 true true 177 40 38
Circle -7500403 true true 177 132 38
Circle -7500403 true true 70 85 38
Circle -7500403 true true 130 25 38
Circle -7500403 true true 96 51 108
Circle -16777216 true false 113 68 74
Polygon -10899396 true false 189 233 219 188 249 173 279 188 234 218
Polygon -10899396 true false 180 255 150 210 105 210 75 240 135 240

house
false
0
Rectangle -7500403 true true 45 120 255 285
Rectangle -16777216 true false 120 210 180 285
Polygon -7500403 true true 15 120 150 15 285 120
Line -16777216 false 30 120 270 120

leaf
false
0
Polygon -7500403 true true 150 210 135 195 120 210 60 210 30 195 60 180 60 165 15 135 30 120 15 105 40 104 45 90 60 90 90 105 105 120 120 120 105 60 120 60 135 30 150 15 165 30 180 60 195 60 180 120 195 120 210 105 240 90 255 90 263 104 285 105 270 120 285 135 240 165 240 180 270 195 240 210 180 210 165 195
Polygon -7500403 true true 135 195 135 240 120 255 105 255 105 285 135 285 165 240 165 195

line
true
0
Line -7500403 true 150 0 150 300

line half
true
0
Line -7500403 true 150 0 150 150

pentagon
false
0
Polygon -7500403 true true 150 15 15 120 60 285 240 285 285 120

person
false
0
Circle -7500403 true true 110 5 80
Polygon -7500403 true true 105 90 120 195 90 285 105 300 135 300 150 225 165 300 195 300 210 285 180 195 195 90
Rectangle -7500403 true true 127 79 172 94
Polygon -7500403 true true 195 90 240 150 225 180 165 105
Polygon -7500403 true true 105 90 60 150 75 180 135 105

plant
false
0
Rectangle -7500403 true true 135 90 165 300
Polygon -7500403 true true 135 255 90 210 45 195 75 255 135 285
Polygon -7500403 true true 165 255 210 210 255 195 225 255 165 285
Polygon -7500403 true true 135 180 90 135 45 120 75 180 135 210
Polygon -7500403 true true 165 180 165 210 225 180 255 120 210 135
Polygon -7500403 true true 135 105 90 60 45 45 75 105 135 135
Polygon -7500403 true true 165 105 165 135 225 105 255 45 210 60
Polygon -7500403 true true 135 90 120 45 150 15 180 45 165 90

square
false
0
Rectangle -7500403 true true 30 30 270 270

square 2
false
0
Rectangle -7500403 true true 30 30 270 270
Rectangle -16777216 true false 60 60 240 240

star
false
0
Polygon -7500403 true true 151 1 185 108 298 108 207 175 242 282 151 216 59 282 94 175 3 108 116 108

target
false
0
Circle -7500403 true true 0 0 300
Circle -16777216 true false 30 30 240
Circle -7500403 true true 60 60 180
Circle -16777216 true false 90 90 120
Circle -7500403 true true 120 120 60

tree
false
0
Circle -7500403 true true 118 3 94
Rectangle -6459832 true false 120 195 180 300
Circle -7500403 true true 65 21 108
Circle -7500403 true true 116 41 127
Circle -7500403 true true 45 90 120
Circle -7500403 true true 104 74 152

triangle
false
0
Polygon -7500403 true true 150 30 15 255 285 255

triangle 2
false
0
Polygon -7500403 true true 150 30 15 255 285 255
Polygon -16777216 true false 151 99 225 223 75 224

truck
false
0
Rectangle -7500403 true true 4 45 195 187
Polygon -7500403 true true 296 193 296 150 259 134 244 104 208 104 207 194
Rectangle -1 true false 195 60 195 105
Polygon -16777216 true false 238 112 252 141 219 141 218 112
Circle -16777216 true false 234 174 42
Rectangle -7500403 true true 181 185 214 194
Circle -16777216 true false 144 174 42
Circle -16777216 true false 24 174 42
Circle -7500403 false true 24 174 42
Circle -7500403 false true 144 174 42
Circle -7500403 false true 234 174 42

turtle
true
0
Polygon -10899396 true false 215 204 240 233 246 254 228 266 215 252 193 210
Polygon -10899396 true false 195 90 225 75 245 75 260 89 269 108 261 124 240 105 225 105 210 105
Polygon -10899396 true false 105 90 75 75 55 75 40 89 31 108 39 124 60 105 75 105 90 105
Polygon -10899396 true false 132 85 134 64 107 51 108 17 150 2 192 18 192 52 169 65 172 87
Polygon -10899396 true false 85 204 60 233 54 254 72 266 85 252 107 210
Polygon -7500403 true true 119 75 179 75 209 101 224 135 220 225 175 261 128 261 81 224 74 135 88 99

wheel
false
0
Circle -7500403 true true 3 3 294
Circle -16777216 true false 30 30 240
Line -7500403 true 150 285 150 15
Line -7500403 true 15 150 285 150
Circle -7500403 true true 120 120 60
Line -7500403 true 216 40 79 269
Line -7500403 true 40 84 269 221
Line -7500403 true 40 216 269 79
Line -7500403 true 84 40 221 269

x
false
0
Polygon -7500403 true true 270 75 225 30 30 225 75 270
Polygon -7500403 true true 30 75 75 30 270 225 225 270

@#$#@#$#@
NetLogo 5.3.1
@#$#@#$#@
@#$#@#$#@
@#$#@#$#@
<experiments>
  <experiment name="modelo-1-1" repetitions="30" runMetricsEveryStep="false">
    <setup>setup</setup>
    <go>go</go>
    <metric>ticks</metric>
    <metric>count solitarios</metric>
    <metric>count sociais</metric>
    <metric>count lideres</metric>
    <metric>nSolMortos</metric>
    <metric>nSocMortos</metric>
    <metric>nLidMortos</metric>
    <metric>(tmp_vida_solitarios / (qnt_ag_solitarios - nSol2Soc))</metric>
    <metric>(tmp_vida_sociais / (qnt_ag_sociais + nLid2Soc + nSol2Soc - nSoc2Lid))</metric>
    <metric>(tmp_vida_lideres / nSoc2Lid)</metric>
    <metric>nSol2Soc</metric>
    <metric>nSoc2Lid</metric>
    <metric>nLid2Soc</metric>
    <metric>nAfastamentos</metric>
    <metric>(nAfastamentos / ticks)</metric>
    <metric>nContatos</metric>
    <metric>(nContatos / ticks)</metric>
    <metric>nLidContatos</metric>
    <metric>(nLidContatos / ticks)</metric>
    <enumeratedValueSet variable="nTicks">
      <value value="500"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_bonus">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_alimentos">
      <value value="15"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_alimentos">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_inicial_ags">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_sociais">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_solitarios">
      <value value="33"/>
      <value value="99"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SempAvanc?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="ComLider?">
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="Agrupar?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_perda_ganho">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SeguirLider?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="alcance_ags">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
  </experiment>
  <experiment name="modelo-1-2" repetitions="30" runMetricsEveryStep="false">
    <setup>setup</setup>
    <go>go</go>
    <metric>ticks</metric>
    <metric>count solitarios</metric>
    <metric>count sociais</metric>
    <metric>count lideres</metric>
    <metric>nSolMortos</metric>
    <metric>nSocMortos</metric>
    <metric>nLidMortos</metric>
    <metric>(tmp_vida_solitarios / (qnt_ag_solitarios - nSol2Soc))</metric>
    <metric>(tmp_vida_sociais / (qnt_ag_sociais + nLid2Soc + nSol2Soc - nSoc2Lid))</metric>
    <metric>(tmp_vida_lideres / nSoc2Lid)</metric>
    <metric>nSol2Soc</metric>
    <metric>nSoc2Lid</metric>
    <metric>nLid2Soc</metric>
    <metric>nAfastamentos</metric>
    <metric>(nAfastamentos / ticks)</metric>
    <metric>nContatos</metric>
    <metric>(nContatos / ticks)</metric>
    <metric>nLidContatos</metric>
    <metric>(nLidContatos / ticks)</metric>
    <enumeratedValueSet variable="nTicks">
      <value value="500"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_bonus">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_alimentos">
      <value value="15"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_alimentos">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_inicial_ags">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_sociais">
      <value value="33"/>
      <value value="99"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_solitarios">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SempAvanc?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="ComLider?">
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="Agrupar?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_perda_ganho">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SeguirLider?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="alcance_ags">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
  </experiment>
  <experiment name="modelo-1-3" repetitions="30" runMetricsEveryStep="false">
    <setup>setup</setup>
    <go>go</go>
    <metric>count solitarios</metric>
    <metric>count sociais</metric>
    <metric>count lideres</metric>
    <metric>nSolMortos</metric>
    <metric>nSocMortos</metric>
    <metric>nLidMortos</metric>
    <metric>(tmp_vida_solitarios / (qnt_ag_solitarios - nSol2Soc))</metric>
    <metric>(tmp_vida_sociais / (qnt_ag_sociais + nLid2Soc + nSol2Soc - nSoc2Lid))</metric>
    <metric>(tmp_vida_lideres / nSoc2Lid)</metric>
    <metric>nSol2Soc</metric>
    <metric>nSoc2Lid</metric>
    <metric>nLid2Soc</metric>
    <metric>nAfastamentos</metric>
    <metric>(nAfastamentos / ticks)</metric>
    <metric>nContatos</metric>
    <metric>(nContatos / ticks)</metric>
    <metric>nLidContatos</metric>
    <metric>(nLidContatos / ticks)</metric>
    <enumeratedValueSet variable="nTicks">
      <value value="500"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_bonus">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_alimentos">
      <value value="15"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_alimentos">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_inicial_ags">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_sociais">
      <value value="33"/>
      <value value="99"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_solitarios">
      <value value="33"/>
      <value value="99"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SempAvanc?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="ComLider?">
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="Agrupar?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_perda_ganho">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SeguirLider?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="alcance_ags">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
  </experiment>
  <experiment name="modelo-2" repetitions="30" runMetricsEveryStep="false">
    <setup>setup</setup>
    <go>go</go>
    <metric>count solitarios</metric>
    <metric>count sociais</metric>
    <metric>count lideres</metric>
    <metric>nSolMortos</metric>
    <metric>nSocMortos</metric>
    <metric>nLidMortos</metric>
    <metric>(tmp_vida_solitarios / (qnt_ag_solitarios - nSol2Soc))</metric>
    <metric>(tmp_vida_sociais / (qnt_ag_sociais + nLid2Soc + nSol2Soc - nSoc2Lid))</metric>
    <metric>(tmp_vida_lideres / nSoc2Lid)</metric>
    <metric>nSol2Soc</metric>
    <metric>nSoc2Lid</metric>
    <metric>nLid2Soc</metric>
    <metric>nAfastamentos</metric>
    <metric>(nAfastamentos / ticks)</metric>
    <metric>nContatos</metric>
    <metric>(nContatos / ticks)</metric>
    <metric>nLidContatos</metric>
    <metric>(nLidContatos / ticks)</metric>
    <enumeratedValueSet variable="nTicks">
      <value value="500"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_bonus">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_alimentos">
      <value value="15"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_alimentos">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_inicial_ags">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_sociais">
      <value value="33"/>
      <value value="99"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_solitarios">
      <value value="0"/>
      <value value="66"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SempAvanc?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="ComLider?">
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="Agrupar?">
      <value value="false"/>
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_perda_ganho">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SeguirLider?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="alcance_ags">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
  </experiment>
  <experiment name="modelo-3" repetitions="30" runMetricsEveryStep="false">
    <setup>setup</setup>
    <go>go</go>
    <metric>count solitarios</metric>
    <metric>count sociais</metric>
    <metric>count lideres</metric>
    <metric>nSolMortos</metric>
    <metric>nSocMortos</metric>
    <metric>nLidMortos</metric>
    <metric>(tmp_vida_solitarios / (qnt_ag_solitarios - nSol2Soc))</metric>
    <metric>(tmp_vida_sociais / (qnt_ag_sociais + nLid2Soc + nSol2Soc - nSoc2Lid))</metric>
    <metric>(tmp_vida_lideres / nSoc2Lid)</metric>
    <enumeratedValueSet variable="nTicks">
      <value value="500"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_bonus">
      <value value="0"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_alimentos">
      <value value="15"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_alimentos">
      <value value="12.5"/>
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="energia_inicial_ags">
      <value value="50"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_sociais">
      <value value="66"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="qnt_ag_solitarios">
      <value value="66"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SempAvanc?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="ComLider?">
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="Agrupar?">
      <value value="true"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="taxa_perda_ganho">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="SeguirLider?">
      <value value="false"/>
    </enumeratedValueSet>
    <enumeratedValueSet variable="alcance_ags">
      <value value="&quot;normal&quot;"/>
    </enumeratedValueSet>
  </experiment>
</experiments>
@#$#@#$#@
@#$#@#$#@
default
0.0
-0.2 0 0.0 1.0
0.0 1 1.0 0.0
0.2 0 0.0 1.0
link direction
true
0
Line -7500403 true 150 150 90 180
Line -7500403 true 150 150 210 180

@#$#@#$#@
0
@#$#@#$#@
