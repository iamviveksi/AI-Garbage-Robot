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
    var data = `cake 0,10 2,2 airFreshener
wine 19,30 2,2 airFreshener
lubricant 51,100 1,1 alcohol
oil 35,56 1,1 alcohol
rot 78,100 1,1 alcohol
wine 81,100 1,1 alcohol
blood 51,100 3,4 bakingSoda
fungus 26,40 3,4 bakingSoda
glue 21,50 3,4 bakingSoda
ink 0,60 3,4 bakingSoda
sauce 41,80 3,4 bakingSoda
wine 31,60 3,4 bakingSoda
coffee 0,5 1,4 cleaningMilk
dressing 6,40 1,4 cleaningMilk
fungus 41,80 1,4 cleaningMilk
grass 16,56 1,4 cleaningMilk
sauce 16,40 1,4 cleaningMilk
oil 10,34 2,4 dishSoap
blood 21,50 1,2 floorSoap
cake 60,100 1,2 floorSoap
dressing 0,5 1,2 floorSoap
mud 46,80 1,2 floorSoap
paste 12,100 1,2 floorSoap
water 46,100 1,2 floorSoap
rot 51,77 1,4 fumigator
glass 0,100 1,4 nothing
paper 0,100 1,4 nothing
sand 0,100 1,4 nothing
water 0,10 1,4 nothing
lubricant 16,50 1,2 paste
mud 0,17 1,2 paste
cake 31,59 1,4 soap
dust 71,100 1,4 soap
grass 0,15 1,4 soap
water 11,45 1,4 soap
oil 57,100 1,1 solvent
wine 61,80 1,1 solvent
coffee 61,100 1,3(bez2) spray
dust 0,40 1,3(bez2) spray
fungus 81,100 1,3(bez2) spray
ink 61,70 1,3 spray
mud 18,45 1,3(bez2) spray
rot 0,25 1,3(bez2) spray
coffee 6,30 3,4 vanish
glue 51,100 3,4 vanish
ink 71,100 3,4 vanish
mud 81,100 3,4 vanish
fungus 0,25 1,3(bez2) vinegar
glue 0,20 1,2 vinegar
sauce 81,100 1,1 vinegar
coffee 31,60 2,4 washingPowder
dressing 41,100 2,4 washingPowder
grass 57,100 2,4 washingPowder
rot 26,50 2,4 washingPowder
blood 0,20 1,4 water
cake 11,30 1,4 water
dust 41,70 1,4 water
lubricant 0,15 1,4 water
oil 0,9 1,4 water
paste 0,11 1,4 water
sauce 0,15 1,4 water
wine 0,18 1,4 water`;

    var lines = data.split("\n");

    var base = ["0", "floor", "wood", "carpet", "carpetCotton"];

    for (var i = 0; i < lines.length; i++) {

        var type = lines[i].split(" ")[0];
        var age = lines[i].split(" ")[1].toString();
        var base_floor = lines[i].split(" ")[2].toString();
        var decision = lines[i].split(" ")[3];

        for (var j = 0; j < 200; j++) {
            var output = type+","+foo(age)+","+base[foo(base_floor)]+","+decision;
            console.log(output);
        };
    };
}