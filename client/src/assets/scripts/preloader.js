window.addEventListener('load',onLoad)

function onLoad() {
  setTimeout(()=>{
    document.getElementById('preloader').remove();
    document.getElementById('app').style.display = 'block';}, 3000)
}
