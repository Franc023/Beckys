import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  // Variable para verificar si el usuario está autenticado
  isLoggedIn(): boolean {
    return localStorage.getItem('token') !== null;
  }

  // Método para cerrar sesión
  logout(): void {
    localStorage.removeItem('token');
    // Puedes agregar redirección a la página de inicio si lo deseas
    window.location.href = '/';
  }
}
