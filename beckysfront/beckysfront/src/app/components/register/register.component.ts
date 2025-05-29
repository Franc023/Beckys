import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ApiService } from '../../services/api.service';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  providers: [ApiService]
})
export class RegisterComponent {
  user = {
    name: '',
    lastName: '',
    email: '',
    phone: '',
    role: 'User',
    password: ''
  };
  
  message = '';
  isError = false;

  constructor(private apiService: ApiService) {}

  onSubmit() {
    this.apiService.registerUser(this.user).subscribe({
      next: (response) => {
        this.message = 'Usuario registrado con éxito';
        this.isError = false;
        // Limpiar el formulario
        this.user = {
          name: '',
          lastName: '',
          email: '',
          phone: '',
          role: 'User',
          password: ''
        };
      },
      error: (error) => {
        this.message = 'Error al registrar usuario: ' + error.message;
        this.isError = true;
      }
    });
  }
}
