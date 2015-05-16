function foo(range) {
    var min = range.split("-")[0];
    var max = range.split("-")[1];
    var parsed_min = parseInt(min);
    var parsed_max = parseInt(max);

    var maxx = parsed_max+1;
    var number = Math.random() * (maxx - parsed_min) + parsed_min;
    return parseInt(number);
}

function get(){

var data = `15|FALSE|1-100|besom
8,11,12|TRUE|1-42|brush
5,2,3,14,4|TRUE|43-100|brush
15|TRUE|1-100|grasper
1,10|TRUE|1-67|pressureWasher
7,3,5,13|TRUE|68-100|pressureWasher
11,7,16|FALSE|1-65|rubber
1,5,3|FALSE|66-100|rubber
4,14,9|FALSE|1-100|sandpaper
2,3,5,15,9|TRUE|1-100|scraper
1,13,10|FALSE|1-31|sponge
2,3,4|FALSE|32-64|sponge
11,7,5|FALSE|65-100|sponge
15|FALSE|1-100|tweezer
7|FALSE|1-22|vacuumCleaner
15,6|FALSE|23-100|vacuumCleaner`;



    var lines = data.split("\n");

    var detergents = ["0","water","floorSoap","soap","spray","cleaningMilk","airFreshener","vanish","bakingSoda","vinegar","washingPowder","paste","alcohol","dishSoap","solvent","nothing","fumigator"];
    for (var i = 0; i < lines.length; i++) {

        var detergents_from_line = lines[i].split("|")[0];
        var is_tall = lines[i].split("|")[1].toLowerCase();
        var size = lines[i].split("|")[2].toString();
        var decision = lines[i].split("|")[3];

        for (var j = 0; j < 500; j++) {

            var detergents_temp = detergents_from_line.split(",");

            var selected_detergent = detergents_temp[Math.floor(Math.random() * detergents_temp.length)];

            var output = detergents[selected_detergent]+","+is_tall+","+foo(size)+","+decision;
            console.log(output);
        };
    };
}