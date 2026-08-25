import React from 'react';

/*
 * Símbolo Combinaí — dois círculos que se aproximam.
 * A lente central (interseção geométrica real, r=17, d=16) é o ponto de encontro.
 * 100% monocromático: acompanha o tema claro/escuro sozinho, sem prop manual.
 */
export function CombinaIcon({ size = 38 }) {
  return (
    <svg width={size} height={size} viewBox="0 0 64 64" fill="none" aria-hidden="true">
      <circle cx="24" cy="32" r="17" strokeWidth="2.5" className="fill-[#E5E5E5] dark:fill-[#262626] stroke-[#171717] dark:stroke-white" />
      <circle cx="40" cy="32" r="17" fill="none" strokeWidth="2.5" className="stroke-[#171717] dark:stroke-white" />
      <path d="M32 17 A17 17 0 0 1 32 47 A17 17 0 0 1 32 17 Z" className="fill-[#171717] dark:fill-white" />
    </svg>
  );
}

export function CombinaLogo({ iconSize = 36, textSize = 'text-xl' }) {
  return (
    <div className="flex items-center gap-2">
      <CombinaIcon size={iconSize} />
      <span
        className={`${textSize} font-extrabold tracking-[-0.045em] text-[#171717] dark:text-white`}
        style={{ fontFamily: 'Manrope, sans-serif' }}
      >
        Combinaí
      </span>
    </div>
  );
}