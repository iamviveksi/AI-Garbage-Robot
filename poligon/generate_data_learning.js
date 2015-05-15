function foo(min, max) {
    var number = Math.random() * (max - min) + min;
    return parseInt(number);
}

function get() {
    for (var i = 0; i < 50; i++) {
        var wetness = foo(43, 87);
        var colorIntensity = foo(43, 64);
        var smellIntensity = foo(65, 67);
        var isSticky = true;
        var isGreasy = false;
        var roughness = foo(7, 15);
        var dangerousBacteries = foo(75, 98);
        var isFruity = false;
        var density = foo(53, 63);
        var type_of_rubbish = "fungus";

        var count = wetness + "," + colorIntensity + "," + smellIntensity + "," + isSticky + "," + isGreasy + "," + roughness + "," + dangerousBacteries + "," + isFruity + "," + density + "," + type_of_rubbish;
        console.log(count);
    }
}
