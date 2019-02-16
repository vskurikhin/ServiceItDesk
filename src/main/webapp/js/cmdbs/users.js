/*
 * users.js
 * This file was last modified at 2019-02-16 23:08 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentUser;

function setTriggers() {
    // Nothing to delete in initial application state
    var btnDelete = $('#btnDelete');
    btnDelete.hide();

    // Register listeners
    $('#btnSearch').click(function() {
        search($('#name').val());
        return false;
    });

    // Trigger search when pressing 'Return' on search key input field
    $('#searchKey').keypress(function(e){
        if(e.which === 13) {
            search($('#name').val());
            e.preventDefault();
            return false;
        }
    });

    $('#btnAdd').click(function() {
        newUser();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#userId').val() === '')
            addUser();
        else
            updateUser();
        return false;
    });

    btnDelete.click(function() {
        deleteUser();
        return false;
    });

    $('#asideList a').click(function() {
        findById($(this).data('identity'));
    });
}

function search(searchKey) {
    if (searchKey === '')
        findAll();
    else
        findByName(searchKey);
}

function newUser() {
    setTimeout(function(){location.reload();}, 500);
}

function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: rootURL,
        dataType: "json", // data type of response
        success: renderList
    });
}

function findAllGroup() {
    console.log('findAllGroup');
    $.ajax({
        type: 'GET',
        url: groupRootURL,
        dataType: "json", // data type of response
        success: renderListGroup
    });
}

function find() {
    findAll();
    findAllGroup();
}

function findByName(searchKey) {
    console.log('findByName: ' + searchKey);
    $.ajax({
        type: 'GET',
        url: rootURL + '/search/' + searchKey,
        dataType: "json",
        success: renderList
    });
}

function findById(id) {
    console.log('findById: ' + id);
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id,
        dataType: "json",
        success: function(data){
            $('#btnDelete').show();
            $('#btnSave').html('Save');
            console.log('findById success: ' + data.name);
            currentUser = data;
            renderDetails(currentUser);
        }
    });
}

function addUser() {
    console.log('addUser');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        statusCode: {
            201: function(data, textStatus, jqXHR){
                console.log("User created successfully" + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#userId').val(data.id);
                setTimeout(function(){location.reload();}, 750);
            },
            406: function(data, textStatus, jqXHR){
                alert('addUser error: ' + textStatus);
            },
            500: function(data, textStatus, jqXHR){
                alert('addUser FATAL error: ' + textStatus);
            }
        }
    });
}

function updateUser() {
    console.log('updateUser');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR){
            console.log("User updated successfully: " + textStatus);
            $('#btnDelete').show();
            setTimeout(function(){location.reload();}, 750);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('updateUser error: ' + textStatus);
        }
    });
}

function deleteUser() {
    console.log('deleteUser');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + $('#userId').val(),
        success: function(data, textStatus, jqXHR){
            alert('User deleted successfully');
            setTimeout(function(){location.reload();}, 750);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('deleteUser error');
        }
    });
}

function renderList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $('#asideList li').remove();
    $.each(list, function(index, user) {
        $('#asideList').append('<li><a href="#" data-identity="' + user.id + '">'+user.name+'</a></li>');
    });
    setTriggers();
}

function renderListGroup(data) {
    console.log('renderListGroup');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var groupSelect = $('#group');
    var groupId = Number(groupSelect.find('option:selected').val());
    console.log('renderListGroup groupId:' + groupId);

    $.each(list, function(index, group) {
        if (groupId !== group.id) {
            console.log('renderListGroup append: ' + group.id + ' ' + group.name); // TODO remove
            groupSelect.append('<option value="' + group.id + '">' + group.name + '</option>');
        }
    });
    $('.dropdown-sin-1').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
}

function renderDetails(user) {
    function renderListGroupSelected(id, name) {
        console.log('renderListGroupSelected');
        $('#group').append('<option value="' + id + '" selected>' + name + '</option>');
    }

    $('#userId').val(user.id);
    $('#name').val(user.name);
    var divDropdownSin1 = $('#div-dropdown-sin-1');
    divDropdownSin1.empty();
    divDropdownSin1.html(
        '<div class="dropdown-sin-1 dropdown-single">' +
        '<select form="userForm" id="group" name="group" style="display:none" placeholder="Select">' +
        '</select></div>'
    );
    renderListGroupSelected(user.group.id, user.group.name);
    findAllGroup();
    $('#description').val(user.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var userId = $('#userId').val();
    var userGroupId = $('#group').find('option:selected').val();
    var userGroupName = $('#group').find('option:selected').text();

    return JSON.stringify({
        "id": userId === "" ? Number("0") : Number(userId),
        "name": $('#name').val(),
        "group": {"id": Number(userGroupId), "name": userGroupName},
        "description": $('#description').val()
    });
}

// Retrieve user list when application starts
jQuery(document).ready(find());

