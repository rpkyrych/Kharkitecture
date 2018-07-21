$(document).ready(function () {
    document.getElementById('images').addEventListener('change', readImage, false);

    $(".preview-images-zone").sortable();

    $(document).on('click', '.image-cancel', function () {
        let no = $(this).data('no');
        $(".preview-image.preview-show-" + no).remove();
    });
});


function readImage() {
    let num = 0;
    if (window.File && window.FileList && window.FileReader) {
        let files = event.target.files; //FileList object
        let output = $(".preview-images-zone");
        for (let i = 0; i < files.length; i++) {
            let file = files[i];
            if (!file.type.match('image')) continue;

            let picReader = new FileReader();

            picReader.addEventListener('load', function (event) {
                let picFile = event.target;
                let html = '<div class="preview-image preview-show-' + num + '">' +
                    '<div class="image-cancel" data-no="' + num + '">x</div>' +
                    '<div class="image-zone"><img id="pro-img-' + num + '" src="' + picFile.result + '"></div>' +
                    '</div>';
                output.append(html);
                num = num + 1;
            });

            picReader.readAsDataURL(file);
        }
        $("#images").val('');
    } else {
        console.log('Browser not support');
    }
}