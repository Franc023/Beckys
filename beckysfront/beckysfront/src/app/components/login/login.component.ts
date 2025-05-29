import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [ApiService]
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };
  message = '';
  isError = false;

  constructor(
    private apiService: ApiService, 
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  onSubmit() {
    this.apiService.loginUser(this.credentials).subscribe({
      next: (response) => {
        this.message = 'Inicio de sesión exitoso';
        this.isError = false;
        // Guardar el token en localStorage solo si estamos en el navegador
        if (isPlatformBrowser(this.platformId) && response.token) {
          localStorage.setItem('token', response.token);
        }
        // Redirigir al dashboard
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.message = 'Error al iniciar sesión: ' + (error.error?.message || error.message);
        this.isError = true;
        console.error('Error en el inicio de sesión', error);
      }
    });
  }
}
