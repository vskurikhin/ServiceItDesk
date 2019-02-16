/*
 * login.js
 * This file was last modified at 2019-02-16 18:58 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

function checkLoginForm() {
    const checked = document.getElementById('login').value !== ''
                 && document.getElementById('password').value !== '';
    if (checked) {
        document.getElementById('submit').removeAttribute('disabled')
    }
    else {
        document.getElementById('submit').setAttribute('disabled', 'disabled')
    }
}

function focusListener(event) {
    event.target.style.background = "#528394"
}

function blurListener(event) {
    event.target.style.background = "";
    checkLoginForm()
}

function checkPassword (form) {
    const password = form.password.value;
    const s_letters = "qwertyuiopasdfghjklzxcvbnm";
    const b_letters = "QWERTYUIOPLKJHGFDSAZXCVBNM";
    const digits = "0123456789";
    const specials = "!@#$%^&*()_-+=\|/.,:;[]{}";
    var is_s = false;
    var is_b = false;
    var is_d = false;
    var is_sp = false;

    for (var i = 0; i < password.length; i++) {
        if (!is_s && s_letters.indexOf(password[i]) !== -1) is_s = true;
        else if (!is_b && b_letters.indexOf(password[i]) !== -1) is_b = true;
        else if (!is_d && digits.indexOf(password[i]) !== -1) is_d = true;
        else if (!is_sp && specials.indexOf(password[i]) !== -1) is_sp = true
    }

    var rating = 0;
    var text = "";
    if (is_s) rating++;
    if (is_b) rating++;
    if (is_d) rating++;
    if (is_sp) rating++;

    if (password.length < 6 && rating < 3) {
        alert("Простой пароль");
        return false
    }
    else if (password.length < 6 && rating >= 3) text = "Средний";
    else if (password.length >= 8 && rating < 3) text = "Средний";
    else if (password.length >= 8 && rating >= 3) text = "Сложный";
    else if (password.length >= 6 && rating === 1) text = "Простой";
    else if (password.length >= 6 && rating > 1 && rating < 4) text = "Средний";
    else if (password.length >= 6 && rating === 4) text = "Сложный";

    console.log(text);

    return true
}
