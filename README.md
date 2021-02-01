###auth-service
Identity management using Spring Security, JWT and MYSQL
<br/><br/><br/>

##API Spec
```
- /api/all
Open for all, just show an welcom message.

-/api/signup
-POST:A user can be signed up with a username, password and an email
Request: Body:
{
	"username": "wxyz",
	"password": "wxyzwxyz",
	"email": "wxyz@wxyz.com"
}
Expected Response::
{
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3eHl6IiwiZXhwIjoxNjEyMTkwMDMyLCJpYXQiOjE2MTIxNTQwMzJ9.UoibDNxJ8t-4W6plcP-RC6FeYejySaVRxmCkJ1uoKaQ",
	"username": "wxyz",
	"email": "wxyz@wxyz.com",
	"roles": []
}
		
-/api/login
-POST:A user can be logged in with username, password or email, password combo
Request: Body:
{
    "username": "admin@autho.com",
    "password": "admin"
}

Expected Response::
For successfull login:
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTYxMjE5MDE5OCwiaWF0IjoxNjEyMTU0MTk4fQ.Z5-RHhqtLFAv1hRYi2vCaox1TNG6z6-8uIz-dmfNxmg",
    "username": "Admin",
    "email": "admin@autho.com",
    "roles": [
        "ADMIN"
    ]
}
For invalid login: 
{
    "timestamp": "2021-02-01T03:06:25.880+00:00",
    "status": 403,
    "error": "Forbidden",
    "message": "",
    "path": "/api/login"
}

-/api/users
-GET: Get available all users
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"
{
    "collection": [
        {
            "userName": "Admin",
            "email": "admin@autho.com",
            "isActive": true
        },
        {
            "userName": "wxyz",
            "email": "wxyz@wxyz.com",
            "isActive": true
        }
    ]
}

-/api/permissions
-POST: create a new permission
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"
Body:
{
    "code": "view_role_3",
    "description": "View role list_3"
}

Expected Response:
{
    "message": "Permission created successfully!"
}


-GET: get available permissions
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"

Expected Response:
{
    "collection": [
        {
            "permissionCode": "CREATE_PERMISSION",
            "description": "Create new permission"
        },
		.
		.
		.
        {
            "permissionCode": "VIEW_USERS",
            "description": "View Users List"
        }
    ]
}

-/api/roles
-POST: create a new role with certain permissions
Request:
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"
Body:
{
    "code": "MODERATOR",
    "description": "Have moderation access"
}

Expected Response:
{
    "message": "Role created successfully!"
}


-GET: get available roles with role-ids
Request:
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"

Expected Response:
{
    "collection": [
        {
            "roleName": "ADMIN",
            "description": "Admin role"
        },
        {
            "roleName": "MODERATOR",
            "description": "Have moderation access"
        }
    ]
}


-/api/users/{userName}/roles
-POST: can add a list of roles to the user
Request:
Header: Authorization: "flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"
Body:
{
    "collection": [
        {
            "code": "MODERATOR_",
            "description": "MODERATOR_2 role"
        }
    ]
}

Expected Response:
{
    "message": "Roles Successfully assigned to user Admin"
}


-GET: get list of roles added to the user
Request:
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"

Expected Response:
{
    "collection": [
        {
            "roleName": "MODERATOR_",
            "description": "MODERATOR_2 role"
        },
        {
            "roleName": "ADMIN",
            "description": "Admin role"
        }
    ]
}


-/api/roles/{roleName}/permissions
-POST: add permissions to a role
Request:
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"
Body:
{
    "collection": [
        {
            "code": "update_user",
            "description": "Update User"
        },
        {
            "code": "view_users",
            "description": "View User List"
        }
    ]
}

Expected Response:
{
    "message": "Permissions Successfully assigned to Role MODERATOR"
}
	
-GET: Get the permissions list assigned to a role
Request:
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"

Expected Response:
{
    "collection": [
        {
            "permissionCode": "UPDATE_USER",
            "description": "Update User"
        },
        {
            "permissionCode": "VIEW_USERS",
            "description": "View Users List"
        }
    ]
}

-/api/users/{roleName}/permissions
-POST: sent with set of permission ids- will return which are allowed and whichare not allowed for the user
Request:
Header: Authorization:"flag TOKEN_RECEIVED_FROM_LOGIN_OR_SINGUP"
Body:
{
    "collection": [
        {
            "code": "VIEW_ROLES",
            "description": "View role list"
        },
        {
            "code": "VIEW_USERS",
            "description": "View Users List"
        }
    ]
}

Expected Response:
{
    "collection": [
        {
            "permissionCode": "VIEW_ROLES",
            "isAllowed": false
        },
        {
            "permissionCode": "VIEW_USERS",
            "isAllowed": false
        }
    ]
}
```