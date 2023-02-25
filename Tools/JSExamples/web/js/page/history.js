import Document from "../modules/entities/Document.js";
/*
import HistoryController from "../modules/controllers/HistoryController.js";

function init() {
    let controller = new HistoryController();
    controller.init();
}

$(document).ready(init);*/
let phoneNumber = {
    'Пожарная служба' : [
        ['Номер 1', '101'],
        ['Номер 2', '112']
    ],
    'Полиция' : [
        ['Номер 1', '102'],
        ['Номер 2', '112']
    ]
}

document.querySelector('.content').innerHTML =`<table class="phone"></table>`
for(let key in phoneNumber) {
    console.log(key)
    let row = document.createElement('tr')
    row.innerHTML = `<td colspan="2">${key}</td>`
    document.querySelector('.phone').appendChild(row)
    for (let i=0; i<phoneNumber[key].length; i++){
        let row = document.createElement('tr')
        row.innerHTML =`
            <td>${phoneNumber[key][i][0]}</td>
            <td>${phoneNumber[key][i][1]}</td>
        `
        document.querySelector('.phone').appendChild(row)
    }
}
let documentsArray = [];
for (let i = 0; i < 3; i++) {
    documentsArray[i]= new Document(String(i), "1" + String(i), "Название_" + String(i+1))

}

document.querySelector('.content2').innerHTML =`<div class="documents"></div>`
for (let i = 0; i < documentsArray.length; i++){
    let _document = documentsArray[i]
    console.log(_document.id)
    let row = document.createElement('div')
    row.innerHTML=`<input type="radio" class="i-7" id=${_document.id} checked name="rb1" value= ${_document.id}>
                    <label for=${_document.id}>${_document.title}</label>`
    document.querySelector('.documents').appendChild(row)

}
let row = document.createElement('div')
row.innerHTML=`<button class="b-7" id="b-7">Выбрать</button>`
document.querySelector('.content3').appendChild(row)
//Обработчик кнопки
document.querySelector('.b-7').addEventListener('click', () => {
    let radio = document.querySelectorAll('.i-7');
    let data = '';
    for (let i=0; i<radio.length; i++){
        if (radio[i].checked){
            data = radio[i].value;
            break;
        }
    }
    document.querySelector('.out-7').innerHTML = data;
})

//С импортом
import { documentsArrayMain } from "../vars.js";
for (let i = 0; i < documentsArrayMain.length; i++) {
    let _document = documentsArrayMain[i]
    console.log(_document.id)
}

document.querySelector('.content4').innerHTML =`<div class="documents"></div>`
for (let i = 0; i < documentsArrayMain.length; i++){
    let _document = documentsArrayMain[i]
    console.log(_document.id)
    let row = document.createElement('div')
    row.innerHTML=`<input type="radio" class="i-8" id=${_document.id} checked name="rb1" value= ${_document.id}>
                    <label for=${_document.id}>${_document.title}</label>`
    document.querySelector('.documents').appendChild(row)

}
row = document.createElement('div')
row.innerHTML=`<button class="b-8" id="b-8">Выбрать</button>`
document.querySelector('.content5').appendChild(row)
//Обработчик кнопки
document.querySelector('.b-8').addEventListener('click', () => {
    let radio = document.querySelectorAll('.i-8');
    let data = '';
    for (let i=0; i<radio.length; i++){
        if (radio[i].checked){
            data = radio[i].value;
            break;
        }
    }
    document.querySelector('.out-8').innerHTML = data;
})