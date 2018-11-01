import os
import subprocess


exp_prefix = [
    'exp01-cenario-10x10-{}.state',
    'exp01-cenario-20x20-{}.state',
    'exp01-cenario-50x50-{}.state',
    'exp01-cenario-100x100-{}.state',
    ]


index_variation = ["{0:0=3d}".format(x) for x in range(1, 101)]

def make_str_exp_all(exp_prefix, index_variation):
    default = "java -jar ./jars/experiment01.jar ./Cenarios/Environment01.oomdp ./Cenarios/exp01/{} {} 10000 ./results/exp01.csv {} {}"

    states = []
    for exp in exp_prefix:
       states += [exp.format(i) for i in index_variation]
    
    for state in states:
        # first run in the state and save its mind
        train_mind = 'trainned_minds/'+state+".trainned.mind"
        os_line = default.format(
            state,
            "no_needed.xml",
            train_mind,
            ""
            )
        #print(os_line)
        subprocess.call(os_line, shell=True)
        # run
        for state_conf in []:#states:
            os_line = default.format(
                state_conf,
                "no_needed.xml",
            "NULL_SAVE",
            train_mind
            )
            print(os_line)
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
