import React, { createContext, useState, useEffect, useContext } from 'react';

// Create Theme Context
const ThemeContext = createContext();

// Custom hook to use theme
export const useTheme = () => {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error('useTheme must be used within ThemeProvider');
  }
  return context;
};

// Theme Provider Component
export const ThemeProvider = ({ children }) => {
  const [isInitialized, setIsInitialized] = useState(false);
  const [darkMode, setDarkMode] = useState(false); // Default to false, will be updated after initialization

  // Initialize theme on mount
  useEffect(() => {
    if (typeof window !== 'undefined') {
      // Check localStorage first
      const savedTheme = localStorage.getItem('theme');
      
      if (savedTheme) {
        // If user has explicitly set a theme, use it
        setDarkMode(savedTheme === 'dark');
      } else {
        // Otherwise, use system preference
        const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        setDarkMode(prefersDark);
      }
      
      setIsInitialized(true);
    }
  }, []);

  // Effect to handle theme changes
  useEffect(() => {
    if (isInitialized && typeof window !== 'undefined') {
      // Save to localStorage
      localStorage.setItem('theme', darkMode ? 'dark' : 'light');
      
      // Add/remove class on document element
      if (darkMode) {
        document.documentElement.classList.add('dark-mode');
      } else {
        document.documentElement.classList.remove('dark-mode');
      }
    }
  }, [darkMode, isInitialized]);

  // Listen for system theme changes (only if no user preference is saved)
  useEffect(() => {
    if (typeof window === 'undefined') return;

    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    
    const handleChange = (e) => {
      // Only update if user hasn't explicitly set a theme
      if (!localStorage.getItem('theme')) {
        setDarkMode(e.matches);
      }
    };

    mediaQuery.addEventListener('change', handleChange);
    return () => mediaQuery.removeEventListener('change', handleChange);
  }, []);

  // Toggle theme function
  const toggleTheme = () => {
    // Save user preference when they manually toggle
    if (typeof window !== 'undefined') {
      localStorage.setItem('theme', darkMode ? 'light' : 'dark');
    }
    setDarkMode(prevMode => !prevMode);
  };

  // Context value
  const value = {
    darkMode,
    toggleTheme,
    theme: darkMode ? 'dark' : 'light',
    isInitialized
  };

  return (
    <ThemeContext.Provider value={value}>
      {children}
    </ThemeContext.Provider>
  );
};