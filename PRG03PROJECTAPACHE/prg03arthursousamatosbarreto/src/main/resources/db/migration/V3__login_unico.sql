/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  PC
 * Created: 19 de nov. de 2026
 */

ALTER TABLE usuarios ADD CONSTRAINT uk_usuarios_login UNIQUE (txtlogin);