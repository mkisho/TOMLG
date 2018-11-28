import os
import subprocess
import random


exp_prefix = [
    'exp01-cenario-10x10-{}.state',
    'exp01-cenario-20x20-{}.state',
    'exp01-cenario-50x50-{}.state',
    'exp01-cenario-100x100-{}.state',
    ]


index_variation = ["{0:0=3d}".format(x) for x in range(1, 101)]

#default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp01/{} {} 10000 ./results/exp03.csv {} {}"

"""
Aprende no cenário 01. 
Testa nos cenários de teste
"""
def agent_no_learning(states_train, states_test, start_mind):
    default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/{} {} 10000 ./results/exp03/agent-no-learning.csv {} {}"
    for state in states_test:
        # first run in the state and save its mind
        os_line = default.format(
            state,
            "no_needed.xml",
            "NULL_SAVE",
            start_mind
            )
        print(os_line)
        subprocess.call(os_line, shell=True)

"""
Aprende no cenário 01.
Aprende continuamente nos cenários de treino
Testa nos cenários de teste
"""
def agente_continuos_learning(states_train, states_test, start_mind):
    default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/{} {} 10000 ./results/exp03/agent-continous-learning-train.csv {} {}"
    train_mind = "trainned_minds/exp03/continuos-learning.trainned.mind"
    os_line = default.format(
            states_train[0],
            "no_needed.xml",
            train_mind,
            start_mind)
    
    subprocess.call(os_line, shell=True)

    # treina nos cenários de treino    
    for state in states_train[1:]:
        # first run in the state and save its mind
        os_line = default.format(
            state,
            "no_needed.xml",
            train_mind,
            train_mind
            )
        print(os_line)
        subprocess.call(os_line, shell=True)

    default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/{} {} 10000 ./results/exp03/agent-continous-learning-test.csv {} {}"

    # testas nos cenários de teste
    for state in states_test:
        # first run in the state and save its mind
        os_line = default.format(
            state,
            "no_needed.xml",
            "NULL_SAVE",
            train_mind
            )
        print(os_line)
        subprocess.call(os_line, shell=True)

"""
Aprende no cenário 01.
Observa continuamente nos cenários de treino
"""
def agente_observador_only(states_train, states_test):
    """default = "java -jar ./jars/experiment03-obfix.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/obs/{} {} 10000 ./results/exp03/exp03-observation-train.csv {} {}"

    train_mind = "trainned_minds/exp03/continuos-obs-train.mind"

    os_line = default.format(
            states_train[0].replace("exp01-cenario", "exp02"),
            "no_needed.xml",
            train_mind,
            "trainned_minds/exp03/only-first.trainned.mind"
        )
    subprocess.call(os_line, shell=True)

    return"""

    

    default = "java -jar ./jars/experiment02.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/obs/{} {} 10000 ./results/exp03/exp03-observation-train.csv {} {}"

    train_mind = "trainned_minds/exp03/continuos-obs-train.mind"
    
    for state in states_train[1:]:
        state = state.replace("exp01-cenario", "exp02")
        # first run in the state and save its mind
        os_line = default.format(
            state,
            "no_needed.xml",
            train_mind,
            train_mind
        )
        subprocess.call(os_line, shell=True)


def agent_transfer(states_train, states_test):
    default = "java -jar ./jars/experiment03.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/{} {} 10000 ./results/exp03/exp03-transfer-test.csv {} {} {}"

    transfer_mind = "trainned_minds/exp03/continuos-obs-train.mind"
    _mind = "trainned_minds/exp03/only-first.trainned.mind"

    for state in states_test:
        #state = state.replace("exp01-cenario", "exp02")
        # first run in the state and save its mind
        os_line = default.format(
            state,
            "no_needed.xml",
            "NULL_SAVE",
            transfer_mind,
            _mind
        )
        subprocess.call(os_line, shell=True)


def make_str_exp_all(exp_prefix, index_variation):
    default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp03/{} {} 10000 ./results/exp03/exp03-01-mind.csv {} {}"
    states = []
    
    states += [exp_prefix[0].format(i) for i in index_variation]

    train_mind = "trainned_minds/exp03/only-first.trainned.mind"

    # treina os 3 agentes no primeiro cenário
    os_line = default.format(
        states[0],
        "no_needed.xml",
        train_mind,
        train_mind
    )
    print(os_line)
    #subprocess.call(os_line, shell=True)

    #remove o primeiro cenário da lista
    f = states[0]
    states.remove(states[0])

    # aleatoriza a lista de cenários
    random.seed(666)
    random.shuffle(states)

    # separa os cenários em um conjunto de teste e treinamento
    states_train = states[0:49]
    states_test  = states[49:]
    start_mind = train_mind
  
    #agent_no_learning(states_train, states_test, start_mind)
    #raw_input(">>")
    #agente_continuos_learning(states_train, states_test, start_mind)
    agente_observador_only([f]+states_train, states_test)
    agent_transfer(states_train, states_test)
if __name__ == "__main__":
    exp_prefix = [
    'exp01-cenario-10x10-{}.state',
    'exp01-cenario-20x20-{}.state',
    'exp01-cenario-50x50-{}.state',
    'exp01-cenario-100x100-{}.state',
    ]


    index_variation = ["{0:0=3d}".format(x) for x in range(1, 101)]

    make_str_exp_all(exp_prefix, index_variation)
