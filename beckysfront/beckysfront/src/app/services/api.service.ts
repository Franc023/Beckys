import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:54968'; // Backend Spring Boot URL

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  private getHeaders(): HttpHeaders {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      if (token) {
        headers = headers.set('Authorization', `Bearer ${token}`);
      }
    }
    
    return headers;
  }

  // Método para guardar un producto
  saveProduct(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/product/v1/saveproduct`, formData);
  }

  // Método para registrar un usuario
  registerUser(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/saveuser`, userData);
  }

  // Método para iniciar sesión
  loginUser(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  // Método para obtener todos los productos
  getAllProducts(): Observable<any> {
    return this.http.get(`${this.apiUrl}/product/v1/allproduct`, { headers: this.getHeaders() });
  }

  // Método para obtener un producto por ID
  getProductById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/product/v1/findbyid/${id}`, { headers: this.getHeaders() });
  }

  // Método para eliminar un producto
  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/product/v1/deletid/${id}`, { headers: this.getHeaders() });
  }
}
