function foo(range) {
    if(range === "1,3(bez2)"){
        var test = !(Math.random()+.5|0);
        if (test === true) {
            return(1);
        } else {
            return(3);
        }
    }

    var min = range.split(",")[0];
    var max = range.split(",")[1];
    var parsed_min = parseInt(min);
    var parsed_max = parseInt(max);

    var maxx = parsed_max+1;
    var number = Math.random() * (maxx - parsed_min) + parsed_min;
    return parseInt(number);
}


function get(){

    var data =`cake 0,10 2,2 0,20 20,100 FALSE airFreshener
wine 19,30 2,2 0,30 10,100 FALSE airFreshener
lubricant 51,100 1,1 10,40 0,30 TRUE alcohol
oil 35,56 1,1 30,65 0,25 TRUE alcohol
rot 78,100 1,1 20,80 0,60 TRUE alcohol
wine 81,100 1,1 70,100 0,45 TRUE alcohol
blood 51,100 3,4 10,90 80,100 TRUE bakingSoda
fungus 26,40 3,4 15,60 50,100 TRUE bakingSoda
glue 21,50 3,4 80,100 40,90 TRUE bakingSoda
ink 0,60 3,4 70,100 60,95 TRUE bakingSoda
sauce 41,80 3,4 40,80 40,100 TRUE bakingSoda
wine 31,60 3,4 30,60 80,100 TRUE bakingSoda
coffee 0,5 1,4 30,90 40,80 FALSE cleaningMilk
dressing 6,40 1,4 25,80 30,100 FALSE cleaningMilk
fungus 41,80 1,4 10,70 60,100 FALSE cleaningMilk
grass 16,56 1,4 0,70 80,100 FALSE cleaningMilk
sauce 16,40 1,4 20,70 50,85 FALSE cleaningMilk
oil 10,34 2,4 10,95 10,80 FALSE dishSoap
blood 21,50 1,2 0,50 20,90 FALSE floorSoap
cake 60,100 1,2 0,60 30,80 FALSE floorSoap
dressing 0,5 1,2 0,70 60,100 FALSE floorSoap
mud 46,80 1,2 0,30 70,100 FALSE floorSoap
paste 12,100 1,2 0,65 50,95 FALSE floorSoap
water 46,100 1,2 0,10 95,100 FALSE floorSoap
rot 51,77 1,4 70,100 0,60 TRUE fumigator
glass 0,100 1,4 0,50 0,100 FALSE nothing
paper 0,100 1,4 0,60 0,100 FALSE nothing
sand 0,100 1,4 0,70 0,100 FALSE nothing
water 0,10 1,4 0,70 0,100 FALSE nothing
lubricant 16,50 1,2 30,60 20,80 FALSE paste
mud 0,17 1,2 20,50 30,75 FALSE paste
cake 31,59 1,4 10,40 60,80 FALSE soap
dust 71,100 1,4 20,70 90,100 FALSE soap
grass 0,15 1,4 0,30 80,95 FALSE soap
water 11,45 1,4 0,10 20,75 FALSE soap
oil 57,100 1,1 10,80 10,50 TRUE solvent
wine 61,80 1,1 0,85 0,30 TRUE solvent
coffee 61,100 1,3(bez2) 30,50 10,65 FALSE spray
dust 0,40 1,3(bez2) 40,70 30,60 FALSE spray
fungus 81,100 1,3(bez2) 10,60 70,95 FALSE spray
ink 61,70 1,3 30,65 30,55 FALSE spray
mud 18,45 1,3(bez2) 20,70 40,75 FALSE spray
rot 0,25 1,3(bez2) 0,80 75,90 FALSE spray
coffee 6,30 3,4 60,100 10,40 TRUE vanish
glue 51,100 3,4 40,90 0,30 TRUE vanish
ink 71,100 3,4 50,95 20,50 TRUE vanish
mud 81,100 3,4 40,100 0,40 TRUE vanish
fungus 0,25 1,3 (bez 2) 30,70 0,50 TRUE vinegar
glue 0,20 1,2 20,45 10,70 TRUE vinegar
sauce 81,100 1,1 40,75 0,20 TRUE vinegar
coffee 31,60 2,4 0,60 30,70 FALSE washingPowder
dressing 41,100 2,4 0,40 50,80 FALSE washingPowder
grass 57,100 2,4 0,50 65,90 FALSE washingPowder
rot 26,50 2,4 0,20 80,100 FALSE washingPowder
blood 0,20 1,4 0,30 90,100 FALSE water
cake 11,30 1,4 0,10 80,100 FALSE water
dust 41,70 1,4 0,40 30,90 FALSE water
lubricant 0,15 1,4 0,10 20,100 FALSE water
oil 0,9 1,4 0,20 10,100 FALSE water
paste 0,11 1,4 0,30 20,90 FALSE water
sauce 0,15 1,4 0,15 50,100 FALSE water
wine 0,18 1,4 0,25 30,100 FALSE water`;

    var lines = data.split("\n");

    var base = ["0", "floor", "wood", "carpet", "carpetCotton"];

    for (var i = 0; i < lines.length; i++) {
        //type //age //base //chemicals //baseState //isPoisonous //class
        var type = lines[i].split(" ")[0];
        var age = lines[i].split(" ")[1].toString();
        var base_floor = lines[i].split(" ")[2].toString();
        var chemicals = lines[i].split(" ")[3].toString();
        var baseState = lines[i].split(" ")[4].toString();
        var isPoisonous = lines[i].split(" ")[5].toLowerCase();
        var decision = lines[i].split(" ")[6];

        for (var j = 0; j < 200; j++) {
            //var output = type+","+foo(age)+","+base[foo(base_floor)]+","+decision;
            var output = type+","+foo(age)+","+base[foo(base_floor)]+","+foo(chemicals)+","+foo(baseState)+","+isPoisonous+","+decision;
            console.log(output);
        };
    };
}