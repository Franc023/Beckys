import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
  providers: [ApiService]
})
export class ProductListComponent implements OnInit {
  products: any[] = [];
  loading = false;
  error = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.loading = true;
    this.apiService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar productos: ' + err.message;
        this.loading = false;
      }
    });
  }
}
