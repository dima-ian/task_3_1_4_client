function fillEditModal(id) {
    $.ajax({
        url: 'http://localhost:8081/api/admin/' + id,
        method: "GET",
        dataType: "json",
        success: function (data) {
            $('#modalId').val(data.id);
            $('#modalName').val(data.name);
            $('#modalLogin').val(data.login);
            $('#modalPassword').val(data.password);
        },
        error: function (error) {
            alert(error);
        }
    })
}