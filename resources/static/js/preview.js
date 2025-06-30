const imageInput = document.getElementById('imageFile');
const imagePreview = document.getElementById('imagePreview');

imageInput.addEventListener('change',() => {	/* ファイルが選択された時に発行 */
	if (imageInput.files[0]) {
		let fileReader = new FileReader();
		fileReader.onload = () => {	/* 読み込み完了後の処理を定義 */
			imagePreview.innerHTML = `<img src="${fileReader.result}" class="mb-3">`;	 /* HTML内にimgタグを挿入 */
		}
		fileReader.readAsDataURL(imageInput.files[0]);	/* ファイルの内容をdata:形式の文字列にする */
	}else {
		imagePreview.innerHTML = '';
	}
})