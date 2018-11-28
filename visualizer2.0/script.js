
var data = null;

$.getJSON('./experiment01.json', function(json) {
    data=json;
    data=data.Minds;
//    console.log(json);

    updateScreenStepId(0); // inicializaÃ§Ã£o

});

function updateScreenStepId(id){
    printPerception(data[id]);
    rintEffects(data[id], "me");
    printGoals(data[id].goals, "me");
}

function printGoals(datax){
    text  = " ";
    if(datax.AchivementGoals != null){
	text = _genericPrintLoop(datax.AchivementGoals);
    }

    if(datax.intention != null){
	text+= "  " +_genericPrintLoop(datax.intention);
    }
    
    
    document.getElementById("reasoning").getElementsByClassName("text")[0].innerHTML = text;
}

function _genericPrintLoop(array){
    text = "";

    for (var index in array){
	text += "$$ "+ array[index]+ " $$ "; //+"</br>";
    }

    return text;
}

function _beliefLoop(array, who){
    text = "";
    for (var index in array){
	text += "$$ Belief_{"+who+"}( "+array[index]+" ) $$"; //+"</br>";
    }
    return text;
}

function printPerception(datax){
    text = _beliefLoop(datax.PerceptionsLowLevel, "me");
    text += _beliefLoop(datax.PerceptionsHighLevel, "me");
    
    document.getElementById("Perceptions").getElementsByClassName("text")[0].innerHTML = text;
}

function printEffects(datax, who){
    text = _beliefLoop(datax.Effects.Effects, who);
       
    document.getElementById("Effects").getElementsByClassName("text")[0].innerHTML = text;
}
