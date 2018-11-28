
var data = null;
var step = 0;
$.getJSON('./experiment01.json', function(json) {
    data=json;
    data=data.Minds;
//    console.log(json);
    updateScreenStepId("primeiro"); // inicializaÃ§Ã£o
});

function updateScreenStepId(event){
    switch(event) {
    case "primeiro":
        step = 0;
        break;
    case "ultimo":
        step = data.length - 1;
        break;
    case "proximo":
        step++;
        break;
    case "anterior":
        step--;
        break;
    default:
    }
    printPerception(data[step]);
    printEffects(data[step], "me");
    printGoals(data[step].goals, "me");
}

function printGoals(datax){
    text  = " ";

    if(datax.AchivementGoals != null){
	text = _genericPrintLoop(datax.AchivementGoals);
    }

    console.log(datax.intention);
    if(datax.intention != null){
	text += " $$"+datax.intention+"$$";
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
    console.log(text);
    //text = text.replace("\\\\", "\\");
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
