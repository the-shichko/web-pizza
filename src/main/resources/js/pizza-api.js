function addToStorage(id){
    let backet = localStorage.getItem('backet');
    backet += $` {id}`;
    localStorage.setItem('backet', backet);
    document.cookie = `backet = `
}