"use client";
import { useState, useEffect } from 'react';
import Image from 'next/image';
import Link from 'next/link';

const PostCards = () => {
    const [screenHeight, setScreenHeight] = useState(0);
    const [posts] = useState([
        {
            id: 1,
            image: "https://via.placeholder.com/400x200",
            title: "Livro Frontend",
            description: "livro de frontend seminovo ...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario1",
        },
        {
            id: 2,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 2",
            description: "Descrição curta do item 2...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario2",
        },
        {
            id: 3,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 3",
            description: "Descrição curta do item 3...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario3",
        },
        {
            id: 4,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 4",
            description: "Descrição curta do item 4...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario4",
        },
        {
            id: 5,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 5",
            description: "Descrição curta do item 5...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario5",
        },
        {
            id: 6,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 6",
            description: "Descrição curta do item 6...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario6",
        },
        {
            id: 7,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 7",
            description: "Descrição curta do item 7...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario7",
        },
        {
            id: 8,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 8",
            description: "Descrição curta do item 8...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario8",
        },
        {
            id: 9,
            image: "https://via.placeholder.com/400x200",
            title: "Título do item 9",
            description: "Descrição curta do item 9...",
            userAvatar: "https://via.placeholder.com/30",
            username: "@usuario9",
        },
    ]);

    useEffect(() => {
        const updateHeight = () => {
            setScreenHeight(window.innerHeight);
        };

        updateHeight();

        window.addEventListener('resize', updateHeight);

        return () => window.removeEventListener('resize', updateHeight);
    }, []);

    return (
        <div className="flex flex-col w-full p-4" style={{ height: `calc(${screenHeight}px - 64px)`, overflowY: 'scroll' }}>
            <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
                {posts.map(post => (
                    <div key={post.id} className="bg-white shadow-md rounded-md overflow-hidden">
                        <Image src={post.image} width={400} height={200} className="w-full h-auto" alt={post.title} />
                        <div className="p-4">
                            <h2 className="text-lg font-bold">{post.title}</h2>
                            <p className="text-gray-600">{post.description}</p>
                            <Link href="#" className="text-blue-600 hover:underline">Ver detalhes</Link>
                            <div className="flex items-center mt-2">
                                <Image src={post.userAvatar} width={30} height={30} className="rounded-full" alt={post.username} />
                                <span className="ml-2 text-gray-600">{post.username}</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default PostCards;
