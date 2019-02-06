/*
 * users.js
 * This file was last modified at 2019-02-04 23:55 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

// rootURL - The root URL for the RESTful services
// Example: var rootURL = "http://localhost:8080/ServiceItDesk/rest/api/v1/users";

var currentUser;

function setTriggers() {
    // Nothing to delete in initial application state
    $('#btnDelete').hide();

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

    $('#btnDelete').click(function() {
        deleteUser();
        return false;
    });

    $('#userList a').click(function() {
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
	$('#btnDelete').hide();
    $('#btnSave').html('Add');
	currentUser = {};
	renderDetails(currentUser); // Display empty form
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
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#userId').val(data.id);
            },
            500: function(data, textStatus, jqXHR){
                alert('addUser FATAL error: ' + textStatus);
            }
        }
	});
}

function updateUser() {
	console.log('updateUser');
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

	$('#userList li').remove();
	$.each(list, function(index, user) {
		$('#userList').append('<li><a href="#" data-identity="' + user.id + '">'+user.name+'</a></li>');
	});
	setTriggers();
}

function renderDetails(user) {
    function renderListGroupSelected(id, name) {
        console.log('renderListGroupSelected');
        // $('#group').remove();
        $('#group').append('<option value="' + id + '" selected>' + name + '</option>');
    }

    function renderListGroup(data) {
        console.log('renderListGroup');
        // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
        var list = data == null ? [] : (data instanceof Array ? data : [data]);

        $.each(list, function(index, group) {
            console.log('append: ' + group.id + ' ' + group.name);
            $('#group').append('<option value="' + group.id + '">' + group.name + '</option>');
        });
        $('.dropdown-sin-1').dropdown({
            readOnly: false,
            input: '<input type="text" maxLength="20" placeholder="Search">'
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

	$('#userId').val(user.id);
	$('#name').val(user.name);
    $('#div-dropdown-sin-1').empty();
    $('#div-dropdown-sin-1').html(
        '<div class="dropdown-sin-1 dropdown-single">' +
        '<select form="userForm" id="group" name="group" style="display:none" placeholder="Select">' +
        '</select></div>'
    );
    // $('.dropdown-sin-1').html(
    //     '<select form="userForm" id="group" name="group" style="display:none" placeholder="Select">' +
    //     '</select>');
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
        "group": {"id": userGroupId, "name": userGroupName, "description": null},
		"description": $('#description').val()
		});
}

// Retrieve user list when application starts
jQuery(document).ready(findAll());

