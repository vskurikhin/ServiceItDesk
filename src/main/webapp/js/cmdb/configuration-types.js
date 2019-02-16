/*
 * configuration-types.js
 * This file was last modified at 2019-02-16 23:09 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

// rootURL - The root URL for the RESTful services
// Example: var rootURL = "http://localhost:8080/ServiceItDesk/rest/api/v1/configurationTypes";

var currentConfigurationType;

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
        newConfigurationType();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#configurationTypeId').val() === '')
            addConfigurationType();
        else
            updateConfigurationType();
        return false;
    });

    btnDelete.click(function() {
        deleteConfigurationType();
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

function newConfigurationType() {
    $('#btnDelete').hide();
    $('#btnSave').html('Add');
    currentConfigurationType = {};
    renderDetails(currentConfigurationType); // Display empty form
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
            currentConfigurationType = data;
            renderDetails(currentConfigurationType);
        }
    });
}

function addConfigurationType() {
    console.log('addConfigurationType');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        statusCode: {
            201: function(data, textStatus, jqXHR){
                console.log("ConfigurationType created successfully" + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#configurationTypeId').val(data.id);
                setTimeout(function(){location.reload();}, 750);
            },
            406: function(data, textStatus, jqXHR){
                alert('addConfigurationType error: ' + textStatus);
            },
            500: function(data, textStatus, jqXHR){
                alert('addConfigurationType FATAL error: ' + textStatus);
            }
        }
    });
}

function updateConfigurationType() {
    console.log('updateConfigurationType');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL,
        dataType: "json",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR){
            console.log("ConfigurationType updated successfully: " + textStatus);
            $('#btnDelete').show();
            setTimeout(function(){location.reload();}, 750);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('updateConfigurationType error: ' + textStatus);
        }
    });
}

function deleteConfigurationType() {
    console.log('deleteConfigurationType');
    $.ajax({
        type: 'DELETE',
        url: rootURL + '/' + $('#configurationTypeId').val(),
        success: function(data, textStatus, jqXHR){
            alert('ConfigurationType deleted successfully');
            setTimeout(function(){location.reload();}, 750);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('deleteConfigurationType error');
        }
    });
}

function renderList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $('#asideList li').remove();
    $.each(list, function(index, configurationType) {
        $('#asideList').append('<li><a href="#" data-identity="' + configurationType.id + '">'+configurationType.name+'</a></li>');
    });
    setTriggers();
}

function renderDetails(configurationType) {
    $('#configurationTypeId').val(configurationType.id);
    $('#name').val(configurationType.name);
    $('#description').val(configurationType.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var configurationTypeId = $('#configurationTypeId').val();

    return JSON.stringify({
        "id": configurationTypeId === "" ? Number("0") : Number(configurationTypeId),
        "name": $('#name').val(),
        "description": $('#description').val()
    });
}

// Retrieve configurationType list when application starts
jQuery(document).ready(findAll());

