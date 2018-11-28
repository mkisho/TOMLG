"""
100 agentes com aprendizagem contnua e com 100 cenários aleatórios
"""
import os
import subprocess
from random import shuffle


exp_prefix = [
    'exp02-10x10-{}.state',
    'exp02-20x20-{}.state',
    'exp02-50x50-{}.state',
    'exp02-100x100-{}.state',
    ]


index_variation = ["{0:0=3d}".format(x) for x in range(1, 101)]

def make_str_exp_all(exp_prefix, index_variation):
    default = "java -jar ./jars/experiment02-mod.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp02/{} {} 10000 ./results/exp02-mod-func.csv {} {}"
    states = []
    for exp in exp_prefix:
       states += [exp.format(i) for i in index_variation]


    for exp_num in range(1, 101):
        print("On exp: ", exp_num)
        train_mind = "trainned_minds/complete.trainned.exp00xxxxxxxx02--mod.{}.mind".format(exp_num)

    
        states_list = states[0:101]
        shuffle(states_list)
    
        for state in states_list:
            # first run in the state and save its mind
            os_line = default.format(
                state,
                "no_needed.xml",
                train_mind,
                train_mind
            )
            subprocess.call(os_line, shell=True)
        

if __name__ == "__main__":
    exp_prefix = [
    'exp02-10x10-{}.state',
    'exp02-20x20-{}.state',
    'exp02-50x50-{}.state',
    'exp02-100x100-{}.state',
    ]


    index_variation = ["{0:0=3d}".format(x) for x in range(1, 101)]

    make_str_exp_all(exp_prefix, index_variation)
