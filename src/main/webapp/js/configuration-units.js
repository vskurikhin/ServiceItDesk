/*
 * configuration-units.js
 * This file was last modified at 2019-02-06 16:16 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

// rootURL - The root URL for the RESTful services
// Example: var rootURL = "http://localhost:8080/ServiceItDesk/rest/api/v1/configurationUnits";

var currentConfigurationUnit;

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
        newConfigurationUnit();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#configurationUnitId').val() === '')
            addConfigurationUnit();
        else
            updateConfigurationUnit();
        return false;
    });

    $('#btnDelete').click(function() {
        deleteConfigurationUnit();
        return false;
    });

    $('#configurationUnitList a').click(function() {
        findById($(this).data('identity'));
    });
}

function search(searchKey) {
	if (searchKey === '')
		findAll();
	else
		findByName(searchKey);
}

function newConfigurationUnit() {
	$('#btnDelete').hide();
    $('#btnSave').html('Add');
	currentConfigurationUnit = {};
	renderDetails(currentConfigurationUnit); // Display empty form
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
			currentConfigurationUnit = data;
			renderDetails(currentConfigurationUnit);
		}
	});
}

function addConfigurationUnit() {
	console.log('addConfigurationUnit');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
        statusCode: {
		    201: function(data, textStatus, jqXHR){
                console.log("ConfigurationUnit created successfully" + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#configurationUnitId').val(data.id);
                setTimeout(function(){location.reload();}, 750);
		    },
            406: function(data, textStatus, jqXHR){
                alert('addConfigurationUnit error: ' + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#configurationUnitId').val(data.id);
            },
            500: function(data, textStatus, jqXHR){
                alert('addConfigurationUnit FATAL error: ' + textStatus);
            }
        }
	});
}

function updateConfigurationUnit() {
	console.log('updateConfigurationUnit');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            console.log("ConfigurationUnit updated successfully: " + textStatus);
            $('#btnDelete').show();
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateConfigurationUnit error: ' + textStatus);
		}
	});
}

function deleteConfigurationUnit() {
	console.log('deleteConfigurationUnit');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#configurationUnitId').val(),
		success: function(data, textStatus, jqXHR){
			alert('ConfigurationUnit deleted successfully');
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteConfigurationUnit error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#configurationUnitList li').remove();
	$.each(list, function(index, configurationUnit) {
		$('#configurationUnitList').append('<li><a href="#" data-identity="' + configurationUnit.id + '">'+configurationUnit.name+'</a></li>');
	});
	setTriggers();
}

function renderDetails(configurationUnit) {
	$('#configurationUnitId').val(configurationUnit.id);
	$('#name').val(configurationUnit.name);

    // TODO function
    $('.dropdown-sin-1').empty();
    $('.dropdown-sin-1').html(
        '<select form="configurationUnitForm" id="admin" name="admin" style="display:none" placeholder="Select1">' +
        '</select>');
    $('#admin').append('<option value="' + configurationUnit.admin.id + '" selected>' + configurationUnit.admin.name + '</option>');
    $('.dropdown-sin-1').dropdown({
        readOnly: true,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });

    $('.dropdown-sin-2').empty();
    $('.dropdown-sin-2').html(
        '<select form="configurationUnitForm" id="owner" name="owner" style="display:none" placeholder="Select1">' +
        '</select>');
    $('#owner').append('<option value="' + configurationUnit.owner.id + '" selected>' + configurationUnit.owner.name + '</option>');
    $('.dropdown-sin-2').dropdown({
        readOnly: true,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });

    $('.dropdown-sin-3').empty();
    $('.dropdown-sin-3').html(
        '<select form="configurationUnitForm" id="group" name="group" style="display:none" placeholder="Select3">' +
        '</select>');
    $('#group').append('<option value="' + configurationUnit.group.id + '" selected>' + configurationUnit.group.name + '</option>');
    $('.dropdown-sin-3').dropdown({
      readOnly: true,
      input: '<input type="text" maxLength="20" placeholder="Search">'
    });

    $('.dropdown-sin-4').empty();
    $('.dropdown-sin-4').html(
        '<select form="configurationUnitForm" id="ctype" name="ctype" style="display:none" placeholder="Select3">' +
        '</select>');
    $('#ctype').append('<option value="' + configurationUnit.type.id + '" selected>' + configurationUnit.type.name + '</option>');
    $('.dropdown-sin-4').dropdown({
        readOnly: true,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });

	$('#description').val(configurationUnit.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var configurationUnitId = $('#configurationUnitId').val();
	return JSON.stringify({
		"id": configurationUnitId === "" ? Number("0") : Number(configurationUnitId),
		"name": $('#name').val(),
        "admin": $('#admin').val(),
        "owner": $('#owner').val(),
        "group": $('#group').val(),
        "type": $('#ctype').val(),
		"description": $('#description').val()
		});
}

// Retrieve configurationUnit list when application starts
jQuery(document).ready(findAll());

