import os
import subprocess

def make_str_exp_all(states):
    default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp {} {} 10000 ./results/exp01-ordered-asc.csv {} {}"

    train_mind = "trainned_minds/complete.trainned.ordered.ASC..mind"

    for state in states[0:101]:
        # first run in the state and save its mind
        os_line = default.format(
            state[0],
            "no_needed.xml",
            train_mind,
            train_mind
            )
        subprocess.call(os_line, shell=True)

    
if __name__ == "__main__":
    exp_prefix = [
    'exp01-cenario-10x10-{}.state',
    'exp01-cenario-20x20-{}.state',
    'exp01-cenario-50x50-{}.state',
    'exp01-cenario-100x100-{}.state',
    ]


    index_variation = ["{0:0=3d}".format(x) for x in range(1, 101)]

    make_str_exp_all(exp_prefix, index_variation)
