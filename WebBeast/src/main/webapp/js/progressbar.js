function move() {
    var elem = document.getElementById("myBar");
    var width = 10;
    var id = setInterval(frame, 40);
    function frame() {
        if (width < 100) {
            width++;
            elem.style.width = width + '%';
            elem.innerHTML = width * 1 + '%';
        } else {
            clearInterval(id);
        }
    }
}
