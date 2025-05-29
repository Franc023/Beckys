import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent {
  productForm: FormGroup;
  imagePreview: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;
  responseMessage: string = '';
  isSuccess: boolean = false;
  showResponse: boolean = false;

  constructor(
    private fb: FormBuilder, 
    private apiService: ApiService,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      season: ['', Validators.required],
      description: ['', Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      stock: [null, [Validators.required, Validators.min(0)]],
      category: ['', Validators.required]
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  onSubmit(): void {
    if (this.productForm.valid && this.selectedFile) {
      const formData = new FormData();
      
      // Crear objeto producto
      const product = {
        name: this.productForm.get('name')?.value,
        season: this.productForm.get('season')?.value,
        description: this.productForm.get('description')?.value,
        price: parseFloat(this.productForm.get('price')?.value),
        stock: parseInt(this.productForm.get('stock')?.value),
        category: this.productForm.get('category')?.value
      };

      // Agregar producto como JSON
      formData.append('product', JSON.stringify(product));
      
      // Agregar archivo de imagen
      formData.append('image', this.selectedFile);

      this.apiService.saveProduct(formData).subscribe({
        next: (response) => {
          this.displayResponse(response, true);
          this.productForm.reset();
          this.imagePreview = null;
          this.selectedFile = null;
          
          // Redirigir después de 5 segundos
          setTimeout(() => {
            this.router.navigate(['/']);
          }, 5000);
        },
        error: (error) => {
          this.displayResponse(error.message || 'Error en la respuesta del servidor', false);
        }
      });
    }
  }

  displayResponse(message: string, isSuccess: boolean): void {
    this.responseMessage = message;
    this.isSuccess = isSuccess;
    this.showResponse = true;

    // Ocultar el mensaje después de 5 segundos
    setTimeout(() => {
      this.showResponse = false;
    }, 5000);
  }
}
