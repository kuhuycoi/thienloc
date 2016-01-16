CKFinder.customConfig = function (config)
{
    config.filebrowserBrowseUrl = 'ckfinder.html',
            config.filebrowserImageBrowseUrl = 'ckfinder.html?type=Images',
            config.filebrowserFlashBrowseUrl = 'ckfinder.html?type=Flash',
            config.filebrowserUploadUrl = '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
            config.filebrowserImageUploadUrl = '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
            config.filebrowserFlashUploadUrl = '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash',
            config.filebrowserWindowWidth = '1000',
            config.filebrowserWindowHeight = '700';
};
