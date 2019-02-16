/*
 * configuration-units.js
 * This file was last modified at 2019-02-16 23:09 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentConfigurationUnit;

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

    btnDelete.click(function() {
        deleteConfigurationUnit();
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

function findAllGroups() {
    console.log('findAllGroups');
    $.ajax({
        type: 'GET',
        url: groupRootURL,
        dataType: "json", // data type of response
        success: renderListGroup
    });
}

function findAllUsers() {
    console.log('findAllUsers');
    $.ajax({
        type: 'GET',
        url: userRootURL,
        dataType: "json", // data type of response
        success: renderListUser
    });
}

function findAllCTypes() {
    console.log('findAllCTypes');
    $.ajax({
        type: 'GET',
        url: configurationTypeRootURL,
        dataType: "json", // data type of response
        success: renderListConfigurationType
    });
}

function find() {
    findAll();
    findAllCTypes();
    findAllGroups();
    findAllUsers();
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
    // noinspection JSUnusedLocalSymbols
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
            },
            500: function(data, textStatus, jqXHR){
                alert('addConfigurationUnit FATAL error: ' + textStatus);
            }
        }
    });
}

function updateConfigurationUnit() {
    console.log('updateConfigurationUnit');
    // noinspection JSUnusedLocalSymbols
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

    $('#asideList li').remove();
    $.each(list, function(index, configurationUnit) {
        $('#asideList').append('<li><a href="#" data-identity="' + configurationUnit.id + '">'+configurationUnit.name+'</a></li>');
    });
    setTriggers();
}

function renderListConfigurationType(data) {
    console.log('renderListGroup');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var ctypeSelect = $('#ctype');
    var typeId = Number(ctypeSelect.find('option:selected').val());

    $.each(list, function(index, type) {
        if (typeId !== type.id) {
            console.log('append: ' + type.id + ' ' + type.name); // TODO remove
            ctypeSelect.append('<option value="' + type.id + '">' + type.name + '</option>');
        }
    });
    $('.dropdown-sin-4').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
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
    $('.dropdown-sin-3').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
}

function renderListUser(data) {
    console.log('renderListUser');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var adminSelect = $('#admin');
    var ownerSelect = $('#owner');
    var adminId = Number(adminSelect.find('option:selected').val());
    var ownerId = Number(ownerSelect.find('option:selected').val());

    $.each(list, function(index, user) {
        if (adminId !== user.id) {
            console.log('renderListUser admin append: ' + user.id + ' ' + user.name);
            adminSelect.append('<option value="' + user.id + '">' + user.name + '</option>');
        }
        if (ownerId !== user.id) {
            console.log('renderListUser owner append: ' + user.id + ' ' + user.name);
            ownerSelect.append('<option value="' + user.id + '">' + user.name + '</option>');
        }
    });
    $('.dropdown-sin-1').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
    $('.dropdown-sin-2').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
}

function renderDetails(configurationUnit) {
    function renderDropdown(num, selectId, selectName, id, name) {
        var divDropdownSin = $('#div-dropdown-sin-' + num);

        divDropdownSin.empty();
        divDropdownSin.html(
            '<div class="dropdown-sin-' + num + ' dropdown-single">' +
            '<select form="configurationUnitForm" id="' +
            selectId + '" name="' + selectName +
            '" style="display:none" placeholder="Select"></select></div>'
        );
        $('#' + selectId).append('<option value="' + id + '" selected>' + name + '</option>');
    }

    console.log('renderDetails');

    $('#configurationUnitId').val(configurationUnit.id);
    $('#name').val(configurationUnit.name);

    renderDropdown('1', 'admin', 'admin', configurationUnit.admin.id, configurationUnit.admin.name);
    renderDropdown('2', 'owner', 'owner', configurationUnit.owner.id, configurationUnit.owner.name);
    findAllUsers();
    renderDropdown('3', 'group', 'group', configurationUnit.group.id, configurationUnit.group.name);
    findAllGroups();
    renderDropdown('4', 'ctype', 'ctype', configurationUnit.type.id, configurationUnit.type.name);
    findAllCTypes();

    $('#description').val(configurationUnit.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var configurationUnitId = $('#configurationUnitId').val();

    var group = $('#group');
    var configurationUnitGroupId = group.find('option:selected').val();
    var configurationUnitGroupName = group.find('option:selected').text();

    var admin = $('#admin');
    var configurationUnitAdminId = admin.find('option:selected').val();
    var configurationUnitAdminName = admin.find('option:selected').text();

    var owner = $('#owner');
    var configurationUnitOwnerId = owner.find('option:selected').val();
    var configurationUnitOwnerName = owner.find('option:selected').text();

    var ctype = $('#ctype');
    var configurationUnitTypeId = ctype.find('option:selected').val();
    var configurationUnitTypeName = ctype.find('option:selected').text();

    return JSON.stringify({
        "id": configurationUnitId === "" ? Number("0") : Number(configurationUnitId),
        "name": $('#name').val(),
        "admin": {"id": Number(configurationUnitAdminId), "name": configurationUnitAdminName, "description": null},
        "owner": {"id": Number(configurationUnitOwnerId), "name": configurationUnitOwnerName, "description": null},
        "group": {"id": Number(configurationUnitGroupId), "name": configurationUnitGroupName, "description": null},
        "type":  {"id": Number(configurationUnitTypeId),  "name": configurationUnitTypeName,  "description": null},
        "description": $('#description').val()
    });
}

// Retrieve configurationUnit list when application starts
jQuery(document).ready(find());

