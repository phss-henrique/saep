import { CanActivateFn, Router } from '@angular/router';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID); // Identifica onde o código está rodando

  // Se estiver rodando no navegador (onde existe localStorage)
  if (isPlatformBrowser(platformId)) {
    const isLogado = localStorage.getItem('usuarioLogado');

    if (isLogado === 'true') {
      return true; // Pode entrar!
    } else {
      router.navigate(['/login']);
      return false; // Bloqueia e volta pro login
    }
  }
  
  // Se estiver rodando no servidor (SSR), deixa passar a renderização inicial 
  // (o navegador assume a verificação logo em seguida)
  return true; 
};